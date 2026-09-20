package com.example.dex_gui.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dex_gui.dto.AutomationExecutionPlan;
import com.example.dex_gui.dto.AutomationExecutionResult;
import com.example.dex_gui.dto.AutomationFormData;
import com.example.dex_gui.dto.ExchangeExecution;
import com.example.dex_gui.dto.ExecutionStep;
import com.example.dex_gui.dto.PartnerContact;
import com.example.dex_gui.dto.QuartetExecution;
import com.example.dex_gui.dto.UserExecution;
import com.example.dex_gui.entity.Exchange;
import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.entity.User;
import com.example.dex_gui.repository.ExchangeRepository;
import com.example.dex_gui.repository.QuartetRepository;
import com.example.dex_gui.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;

import jakarta.transaction.Transactional;

@Service
public class AutomationService {

    private final OpenAIClient openAIClient;
    private final String hiveApiKey;

    private final QuartetRepository quartetRepository;
    private final UserRepository userRepository;
    private final ExchangeRepository exchangeRepository;


    public AutomationService(
            OpenAIClient openAIClient,
            QuartetRepository quartetRepository,
            UserRepository userRepository,
            ExchangeRepository exchangeRepository) {

        this.openAIClient = openAIClient;
        this.quartetRepository = quartetRepository;
        this.userRepository = userRepository;
        this.exchangeRepository = exchangeRepository;
        this.hiveApiKey = "gqt6Mi2hjV4+5MNBSJ6dgg==";
    }

    public AutomationFormData analyzeOpenAI(MultipartFile file) {

        try {

            System.out.println("File received: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());

            ResponseCreateParams params =
                    ResponseCreateParams.builder()
                            .model(ChatModel.GPT_4O)
                            .input("Respond with exactly: AI_CONNECTION_SUCCESS")
                            .build();

            var response = openAIClient.responses().create(params);

            System.out.println("========== AI RESPONSE ==========");
            System.out.println(response);
            System.out.println("=================================");

            return new AutomationFormData();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "AI analysis failed: " + e.getMessage(),
                    e
            );
        }
    }

    public AutomationFormData analyze(MultipartFile file) {

        try {

            System.out.println("File received: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());

            byte[] fileBytes = file.getBytes();

            String base64Image = Base64.getEncoder().encodeToString(fileBytes);

            String mimeType = file.getContentType();

            if (mimeType == null) {
                mimeType = "image/png";
            }

            String imageData = "data:" + mimeType + ";base64," + base64Image;

            String prompt = """
                    You are an AI assistant that extracts information from a Partner Onboarding Form image.
                    
                    Extract the information into the exact JSON structure below.
                    
                    Rules:
                    1. Return ONLY valid JSON.
                    2. Do not return Markdown.
                    3. Do not use ```json or ``` blocks.
                    4. Do not add explanations.
                    5. If a field is not present or cannot be determined, use null for strings.
                    6. For lists, return an empty array [] if there are no entries.
                    7. Preserve the values exactly as they appear in the form.
                    8. For partnerAction, use only ADD, UPDATE, or CLOSE when clearly indicated.
                    9. For user action, preserve values such as ADD, UPDATE, NO_CHANGE.
                    10. If the form indicates SFTP, including values such as "*SFTP", "SFTP", "SFTP Partner", return exactly "SFTP".
                    11. Do not guess obscured email addresses.
                    
                    Return exactly this structure:
                    
                    {
                      "partnerAction": "",
                      "businessUnit": "",
                      "department": "",
                      "compound": "",
                      "study": "",
                      "partnerType": "",
                      "partnerCompanyName": "",
                      "partnerIpRange": "",
                      "users": [
                        {
                          "action": "",
                          "studyOwner": "",
                          "name": "",
                          "eisaiId": "",
                          "phone": "",
                          "email": "",
                          "notify": false
                        }
                      ],
                      "partnerContacts": [
                        {
                          "action": "",
                          "firstName": "",
                          "lastName": "",
                          "email": "",
                          "phone": "",
                          "notification": false,
                          "sftpCredentials": false
                        }
                      ]
                    }
                    """;

            String jsonBody = """
                    {
                      "model": "zai-org/glm-5.3-flash",
                      "temperature": 0,
                      "stream": true,
                      "max_tokens": 4096,
                      "messages": [
                        {
                          "role": "user",
                          "content": [
                            {
                              "type": "text",
                              "text": %s
                            },
                            {
                              "type": "image_url",
                              "image_url": {
                                "url": %s
                              }
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(
                    toJsonString(prompt),
                    toJsonString(imageData)
            );

            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api-cdn.thehive.ai/api/v3/chat/completions"))
                    .header("Authorization", "Bearer " + hiveApiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            StringBuilder aiResponse = new StringBuilder();

            ObjectMapper mapper = new ObjectMapper();

            for (String line : responseBody.split("\\R")) {

                if (!line.startsWith("data:")) {
                    continue;
                }

                String data = line.substring(5).trim();

                if ("[DONE]".equals(data)) {
                    break;
                }

                try {

                    JsonNode root = mapper.readTree(data);

                    JsonNode delta = root
                            .path("choices")
                            .path(0)
                            .path("delta");

                    // Ignore reasoning_content
                    JsonNode content = delta.path("content");

                    if (!content.isMissingNode()
                            && !content.isNull()
                            && !content.asText().isEmpty()) {

                        String text = content.asText();

                        aiResponse.append(text);

                        System.out.print(text);
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Could not parse chunk: " + data
                    );
                }
            }

            System.out.println();
            System.out.println("========== AI RESPONSE ==========");
            System.out.println(aiResponse);
            System.out.println("=================================");

            if (aiResponse.isEmpty()) {
                throw new RuntimeException(
                        "Hive returned no AI content. HTTP status: "
                                + response.statusCode()
                );
            }

            ObjectMapper jsonToFormConverter = new ObjectMapper();

            return jsonToFormConverter.readValue(
                    aiResponse.toString(),
                    AutomationFormData.class
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to analyze automation form: " + e.getMessage(), e);
        }
    }

    public AutomationFormData analyzeText(MultipartFile file) {

        try {

            String dummyData = """
                    {
                        "partnerAction": "ADD",
                        "businessUnit": "OBG",
                        "department": "biostats",
                        "compound": "e2027",
                        "study": "a001-506",
                        "partnerType": "internal",
                        "partnerCompanyName": "dmreview",
                        "partnerIpRange": "10.25.40.0/24",
                        "users": [],
                        "partnerContacts": []
                    }
                    """;

            ObjectMapper jsonToFormConverter = new ObjectMapper();

            return jsonToFormConverter.readValue(
                    dummyData,
                    AutomationFormData.class
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to analyze automation form: " + e.getMessage(), e);
        }
    }

    private String toJsonString(String value) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(value);
    }

    public AutomationExecutionPlan createExecutionPlan(
            AutomationFormData formData) {

        AutomationExecutionPlan plan = new AutomationExecutionPlan();
        plan.setCanProceed(true);

        // =====================================================
        // 1. QUARTET CHECK
        // =====================================================

        Optional<Quartet> quartetOptional =
                quartetRepository
                        .findByCompoundAndStudyAndDepartmentAndPartner(
                                formData.getCompound(),
                                formData.getStudy(),
                                formData.getDepartment(),
                                formData.getPartnerCompanyName()
                        );

        plan.setPartnerType(formData.getPartnerType());
        QuartetExecution quartet = new QuartetExecution();

        quartet.setBusinessUnit(formData.getBusinessUnit());
        quartet.setCompound(formData.getCompound());
        quartet.setStudy(formData.getStudy());
        quartet.setDepartment(formData.getDepartment());
        quartet.setPartner(formData.getPartnerCompanyName());

        if (quartetOptional.isPresent()) {

            Quartet existingQuartet = quartetOptional.get();
            quartet.setExists(true);
            quartet.setId(existingQuartet.getId());
            quartet.setWillCreate(false);

        } else {

            quartet.setExists(false);
            quartet.setId(null);
            quartet.setWillCreate(true);
        }

        plan.setQuartet(quartet);


        // =====================================================
        // 2. USER CHECK
        // =====================================================

        List<UserExecution> userExecutions = new ArrayList<>();

        if (formData.getPartnerContacts() != null) {
            for (PartnerContact contact : formData.getPartnerContacts()) {
                // Ignore empty contacts
                if (contact.getEmail() == null ||
                        contact.getEmail().isBlank()) {
                    continue;
                }
                UserExecution user = new UserExecution();
                user.setFirstName(contact.getFirstName());
                user.setLastName(contact.getLastName());
                user.setEmail(contact.getEmail());
                user.setNotification(contact.isNotification());
                user.setSftpCredentials(contact.isSftpCredentials());


                // ---------------------------------------------
                // Search database
                // ---------------------------------------------

                Optional<User> existingUser = userRepository.findByEmail(contact.getEmail());

                if (existingUser.isPresent()) {

                    User dbUser = existingUser.get();
                    user.setExists(true);
                    user.setUserId(dbUser.getId());
                    user.setWillCreate(false);
                    user.setAction("EXISTING");

                } else {
                    user.setExists(false);
                    user.setUserId(null);
                    user.setWillCreate(true);
                    user.setAction("ADD");
                }

                userExecutions.add(user);
            }
        }

        plan.setUsers(userExecutions);


        // =====================================================
        // 3. EXCHANGE CHECK
        // =====================================================

        ExchangeExecution exchange = new ExchangeExecution();
        exchange.setWillCreate(true);
        plan.setExchange(exchange);

        // =====================================================
        // 4. STEPS
        // =====================================================

        boolean isSFTP = Objects.equals(plan.getPartnerType(), "SFTP");
        List<ExecutionStep> steps = new ArrayList<>();

        steps.add(
                new ExecutionStep(
                        1,
                        "Check Quartet",
                        quartet.isExists()
                                ? "Quartet already exists. Existing quartet ID: "
                                + quartet.getId()
                                : "Quartet does not exist and will be created.",
                        "READY"
                )
        );


        steps.add(
                new ExecutionStep(
                        2,
                        "Check Users",
                        userExecutions.size()
                                + " partner user(s) checked. "
                                + countExistingUsers(userExecutions)
                                + " already exist and "
                                + countNewUsers(userExecutions)
                                + " will be created.",
                        "READY"
                )
        );


        steps.add(
                new ExecutionStep(
                        3,
                        "Create Exchange",
                        "Exchange does not exist and will be created.",
                        "READY"
                )
        );


        steps.add(
                new ExecutionStep(
                        4,
                        "Grant Quartet Access",
                        isSFTP ? "Quartet access will be granted to " + userExecutions.size() + " partner user(s)." : "Separate quartet access is not required for Internal partner type",
                        isSFTP ? "READY" : "NOT Required"
                )
        );


        plan.setSteps(steps);

        // =====================================================
        // 5. SUMMARY
        // =====================================================

        plan.setSummary(
                buildSummary(
                        quartet,
                        userExecutions,
                        exchange
                )
        );

        return plan;
    }

    private long countExistingUsers(List<UserExecution> users) {
        return users.stream().filter(UserExecution::isExists).count();
    }

    private long countNewUsers(List<UserExecution> users) {
        return users.stream().filter(UserExecution::isWillCreate).count();
    }

    private String buildSummary(QuartetExecution quartet, List<UserExecution> users, ExchangeExecution exchange) {

        long existingUsers = countExistingUsers(users);
        long newUsers = countNewUsers(users);
        StringBuilder summary = new StringBuilder();
        summary.append("Automation analysis completed. ");

        if (quartet.isExists()) {
            summary.append("Quartet already exists with ID " + quartet.getId() + ". ");
        } else {
            summary.append("Quartet will be created. ");
        }

        summary.append(existingUsers + " user(s) already exist and " + newUsers + " user(s) will be created. ");

        if (exchange.isExists()) {
            summary.append("Exchange already exists.");
        } else {
            summary.append("A new exchange will be created.");
        }

        return summary.toString();
    }

    @Transactional
    public AutomationExecutionResult execute(AutomationExecutionPlan plan) {

        AutomationExecutionResult result = new AutomationExecutionResult();
        List<ExecutionStep> steps = new ArrayList<>();

        try {

            // =====================================================
            // STEP 1 - QUARTET
            // =====================================================

            boolean isSFTP = Objects.equals(plan.getPartnerType(), "SFTP");
            Quartet quartet;

            if (plan.getQuartet().isExists()) {

                quartet = quartetRepository.findById(plan.getQuartet().getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Quartet not found"
                                )
                        );

            } else {

                quartet = new Quartet();
                quartet.setBusinessUnit(plan.getQuartet().getBusinessUnit());
                quartet.setCategory("Clinical");
                quartet.setCompound(plan.getQuartet().getCompound());
                quartet.setStudy(plan.getQuartet().getStudy());
                quartet.setDepartment(plan.getQuartet().getDepartment());
                quartet.setPartner(plan.getQuartet().getPartner());
                quartet = quartetRepository.save(quartet);
            }


            // VERY IMPORTANT
            // Update the plan with the actual DB ID
            plan.getQuartet().setId(quartet.getId());
            plan.getQuartet().setExists(true);
            plan.getQuartet().setWillCreate(false);

            steps.add(new ExecutionStep(1,
                    "Create / Verify Quartet",
                    "Quartet created successfully and available with ID " + quartet.getId(),
                    "COMPLETED"
            ));


            // =====================================================
            // STEP 2 - USERS
            // =====================================================

            List<User> usersForAccess = new ArrayList<>();

            for (UserExecution userExecution : plan.getUsers()) {

                User user;

                if (userExecution.isExists()) {
                    user = userRepository
                            .findByEmail(userExecution.getEmail())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "User not found: " + userExecution.getEmail()
                                    )
                            );

                } else {

                    user = new User();
                    user.setFirstName(userExecution.getFirstName());
                    user.setLastName(userExecution.getLastName());
                    user.setEmail(userExecution.getEmail());
                    user = userRepository.save(user);
                }

                // Update execution information

                userExecution.setUserId(user.getId());

                userExecution.setExists(true);
                userExecution.setWillCreate(false);
                userExecution.setAction("EXISTING");

                usersForAccess.add(user);
            }


            steps.add(
                    new ExecutionStep(
                            2,
                            "Create / Verify Users",
                            usersForAccess.size() + " user(s) were created successfully.",
                            "COMPLETED"
                    )
            );


            // =====================================================
            // STEP 3 - CREATE EXCHANGE
            // =====================================================

            Exchange exchange;

            if (plan.getExchange().isExists()) {

                // If you later implement lookup-by-ID,
                // retrieve the existing exchange here.
                throw new RuntimeException("Existing exchange execution is not implemented yet");

            } else {

                // Create Exchange
                exchange = new Exchange();

                // Source details
                exchange.setSourceExchangeType(isSFTP ? "mina" : "Other");
                exchange.setSourceTrigger("Event");
                exchange.setSourcePollingFrequency(15);
                exchange.setSourceExchangeNameSuffix("");
                exchange.setSourceEndpoint(isSFTP ? "mina" : "internal");
                exchange.setSourceInboundPath("");
                exchange.setSourceOutboundPath("");
                exchange.setSourceFileNameSuffix("");
                exchange.setSourceEnabled(true);
                exchange.setSourceExtractZip(false);

                // Destination details
                exchange.setDestinationExchangeType("ecce");
                exchange.setDestinationTrigger("Event");
                exchange.setDestinationPollingFrequency(15);
                exchange.setDestinationEndpoint("ecce");
                exchange.setDestinationInboundPath("");
                exchange.setDestinationOutboundPath("");
                exchange.setDestinationFileNameSuffix("");
                exchange.setDestinationEnabled(true);
                exchange.setDestinationExtractZip(false);

                // Same Quartet for source and destination
                exchange.setSourceQuartet(quartet);
                exchange.setDestinationQuartet(quartet);

                // Other
                exchange.setBidirectional(true);

                for (UserExecution userExecution : plan.getUsers()) {
                    User user = userRepository
                            .findByEmail(userExecution.getEmail())
                            .orElseThrow(() -> new RuntimeException("User not found with email: " + userExecution.getEmail()));

                    exchange.addNotificationUser(user);
                }

                exchange = exchangeRepository.save(exchange);
            }

            steps.add(
                    new ExecutionStep(
                            3,
                            "Create Exchange",
                            "Exchange created successfully.",
                            "COMPLETED"
                    )
            );


            // =====================================================
            // STEP 4 - GRANT QUARTET ACCESS
            // =====================================================

            if(isSFTP){
                for (User user : usersForAccess) {
                    user.getQuartets().add(quartet);
                    userRepository.save(user);
                }

                steps.add(
                        new ExecutionStep(
                                4,
                                "Grant Quartet Access",
                                "Successfully granted quartet access to "
                                        + usersForAccess.size()
                                        + " user(s).",
                                "COMPLETED"
                        )
                );
            }

            // =====================================================
            // SUCCESS
            // =====================================================

            result.setSuccess(true);

            result.setMessage("Automation executed successfully.");
            result.setSteps(steps);
            return result;

        } catch (Exception e) {

            result.setSuccess(false);
            result.setMessage("Automation failed: " + e.getMessage());
            result.setSteps(steps);
            return result;
        }
    }

}
