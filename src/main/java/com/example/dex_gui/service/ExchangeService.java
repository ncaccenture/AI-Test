package com.example.dex_gui.service;

import com.example.dex_gui.dto.ExchangeRequest;
import com.example.dex_gui.dto.ExchangeSearchRequest;
import com.example.dex_gui.entity.Exchange;
import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.entity.User;
import com.example.dex_gui.repository.ExchangeRepository;
import com.example.dex_gui.repository.QuartetRepository;
import com.example.dex_gui.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final QuartetRepository quartetRepository;
    private final UserRepository userRepository;

    public ExchangeService(
            ExchangeRepository exchangeRepository,
            QuartetRepository quartetRepository,
            UserRepository userRepository
    ) {

        this.exchangeRepository = exchangeRepository;
        this.quartetRepository = quartetRepository;
        this.userRepository = userRepository;
    }


    public Exchange createExchange(ExchangeRequest request) {

        // Find Quartet using compound, study, department and partner
        Quartet quartet = quartetRepository
                .findByCompoundAndStudyAndDepartmentAndPartner(
                        request.getCompound(),
                        request.getStudy(),
                        request.getDepartment(),
                        request.getPartner()
                )
                .orElseThrow(() ->
                        new RuntimeException("Quartet not found"));


        // Create Exchange
        Exchange exchange = new Exchange();


        // Source details
        exchange.setSourceExchangeType(request.getSourceExchangeType());
        exchange.setSourceTrigger(request.getSourceTrigger());
        exchange.setSourcePollingFrequency(request.getSourcePollingFrequency());
        exchange.setSourceExchangeNameSuffix(request.getSourceExchangeNameSuffix());
        exchange.setSourceEndpoint(request.getSourceEndpoint());
        exchange.setSourceInboundPath(request.getSourceInboundPath());
        exchange.setSourceOutboundPath(request.getSourceOutboundPath());
        exchange.setSourceFileNameSuffix(request.getSourceFileNameSuffix());
        exchange.setSourceEnabled(request.isSourceEnabled());
        exchange.setSourceExtractZip(request.isSourceExtractZip());


        // Destination details
        exchange.setDestinationExchangeType(request.getDestinationExchangeType());
        exchange.setDestinationTrigger(request.getDestinationTrigger());
        exchange.setDestinationPollingFrequency(request.getDestinationPollingFrequency());
        exchange.setDestinationEndpoint(request.getDestinationEndpoint());
        exchange.setDestinationInboundPath(request.getDestinationInboundPath());
        exchange.setDestinationOutboundPath(request.getDestinationOutboundPath());
        exchange.setDestinationFileNameSuffix(request.getDestinationFileNameSuffix());
        exchange.setDestinationEnabled(request.isDestinationEnabled());
        exchange.setDestinationExtractZip(request.isDestinationExtractZip());

        // Same Quartet for source and destination
        exchange.setSourceQuartet(quartet);
        exchange.setDestinationQuartet(quartet);

        // Other
        exchange.setBidirectional(request.isBidirectional());

        if (request.getNotifyEmails() != null) {
            for (String email : request.getNotifyEmails()) {
                User user = userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with email: " + email
                                )
                        );

                exchange.addNotificationUser(user);
            }
        }


        return exchangeRepository.save(exchange);
    }

    public Exchange searchExchange(ExchangeSearchRequest request) {

        return exchangeRepository
                .findBySourceQuartet_CompoundAndSourceQuartet_StudyAndSourceQuartet_DepartmentAndSourceQuartet_PartnerAndSourceEndpointAndDestinationEndpoint(
                        request.getCompound(),
                        request.getStudy(),
                        request.getDepartment(),
                        request.getPartner(),
                        request.getSourceEndpoint(),
                        request.getDestinationEndpoint()
                )
                .orElseThrow(() ->
                        new RuntimeException("Exchange not found"));
    }

    public List<Exchange> getAllExchanges() {
        return exchangeRepository.findAll();
    }
}