document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("exchangeForm");
    const emailInput = document.getElementById("notifyEmailInput");
    const emailList = document.getElementById("notifyEmailList");
    const addEmailButton = document.getElementById("addNotifyEmail");
    const notifyEmails = new Set();
    const quartetFields = [
        ["sourceCompound", "destinationCompound"],
        ["sourceStudy", "destinationStudy"],
        ["sourceDepartment", "destinationDepartment"],
        ["sourcePartner", "destinationPartner"],
    ];

    function syncQuartetField(sourceId, destinationId) {
        const sourceField = document.getElementById(sourceId);
        const destinationField = document.getElementById(destinationId);
        destinationField.value = sourceField.value;
    }

    quartetFields.forEach(([sourceId, destinationId]) => {
        const sourceField = document.getElementById(sourceId);
        sourceField.addEventListener("input", () =>
            syncQuartetField(sourceId, destinationId),
        );
        sourceField.addEventListener("change", () =>
            syncQuartetField(sourceId, destinationId),
        );
        syncQuartetField(sourceId, destinationId);
    });

    function renderNotifyEmails() {
        emailList.replaceChildren();

        notifyEmails.forEach((email) => {
            const emailBadge = document.createElement("span");
            emailBadge.className = "notify-email badge text-bg-light border";

            const emailText = document.createElement("span");
            emailText.textContent = email;

            const removeButton = document.createElement("button");
            removeButton.type = "button";
            removeButton.className = "btn-close";
            removeButton.setAttribute("aria-label", `Remove ${email}`);
            removeButton.addEventListener("click", () => {
                notifyEmails.delete(email);
                renderNotifyEmails();
            });

            emailBadge.append(emailText, removeButton);
            emailList.appendChild(emailBadge);
        });
    }

    function addNotifyEmail() {
        const email = emailInput.value.trim();
        emailInput.value = email;
        emailInput.setCustomValidity("");

        if (!email || !emailInput.checkValidity()) {
            emailInput.setCustomValidity("Enter a valid email address.");
            emailInput.reportValidity();
            emailInput.setCustomValidity("");
            return;
        }

        notifyEmails.add(email);
        emailInput.value = "";
        renderNotifyEmails();
    }

    addEmailButton.addEventListener("click", addNotifyEmail);
    emailInput.addEventListener("keydown", (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            addNotifyEmail();
        }
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const exchangeDetails = {};

        form.querySelectorAll("input[id], select[id], textarea[id]").forEach((field) => {
            exchangeDetails[field.id] =
                field.type === "checkbox" ? field.checked : field.value;
        });

        exchangeDetails.compound = exchangeDetails.sourceCompound;
        exchangeDetails.study = exchangeDetails.sourceStudy;
        exchangeDetails.department = exchangeDetails.sourceDepartment;
        exchangeDetails.partner = exchangeDetails.sourcePartner;
        exchangeDetails.notifyEmails = [...notifyEmails];

        console.log("Exchange details:", exchangeDetails);

        try {
            const response = await fetch("/api/exchange/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(exchangeDetails)
            });

            const result = await response.json();

            document.getElementById("responseModalBody").textContent = result.message;
        } catch (error) {
            console.error(error);
            document.getElementById("responseModalBody").textContent =
                "Something went wrong!";
        }

        const modal = new bootstrap.Modal(
            document.getElementById("responseModal")
        );
        modal.show();
    });
});
