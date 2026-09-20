document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);

    const fieldMapping = {
        sourceCompound: "compound",
        sourceStudy: "study",
        sourceDepartment: "department",
        sourcePartner: "partner",
        sourceEndpoint: "sourceEndpoint",
        destinationCompound: "compound",
        destinationStudy: "study",
        destinationDepartment: "department",
        destinationPartner: "partner",
        destinationEndpoint: "destinationEndpoint"
    };

    Object.entries(fieldMapping).forEach(([fieldId, paramName]) => {

        const field = document.getElementById(fieldId);

        if (field) {
            field.value = params.get(paramName) || "";
        }

    });

    const form = document.getElementById("exchangeForm");
    const sourceEndpoint = document.getElementById("sourceEndpoint");
    const destinationEndpoint = document.getElementById("destinationEndpoint");
    const compound = document.getElementById("sourceCompound");
    const study = document.getElementById("sourceStudy");
    const department = document.getElementById("sourceDepartment");
    const partner = document.getElementById("sourcePartner");

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

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const exchangeDetails = {};

        exchangeDetails["sourceEndpoint"] = sourceEndpoint.value;
        exchangeDetails["destinationEndpoint"] = destinationEndpoint.value;
        exchangeDetails["compound"] = compound.value;
        exchangeDetails["study"] = study.value;
        exchangeDetails["department"] = department.value;
        exchangeDetails["partner"] = partner.value;

        document.getElementById("exchangeResult").style.display = "none";

        try {
            const response = await fetch("/api/exchange/search", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(exchangeDetails)
            });

            const result = await response.json();
            if (!response.ok) {
                throw new Error(result.message || "Exchange not found");
            }

            const exchange = result.data;

            populateExchange(exchange);
            document.getElementById("exchangeResult").style.display = "block";

        } catch (error) {
            console.error(error);
            document.getElementById("responseModalBody").textContent =
                error.message || "Something went wrong!";
            const modal = new bootstrap.Modal(
                document.getElementById("responseModal")
            );

            modal.show();
        }
    });

    function populateExchange(exchange) {

        const uuid = exchange.id;

        // Get last 4 characters of UUID
        const uuid4 = uuid.replaceAll("-", "").slice(-4);

        const sourceEndpoint = exchange.sourceEndpoint;
        const destinationEndpoint = exchange.destinationEndpoint;


        // ========================================
        // Exchange 1
        // UUID + 4 digit + destination + UUID + source
        // ========================================

        const exchange1Name =
            `EX_${uuid4}_${destinationEndpoint}_${uuid4}_${sourceEndpoint}`;

        document.getElementById("exchange1Name").textContent =
            exchange1Name;


        // ========================================
        // Exchange 2
        // UUID + 4 digit + source + UUID + destination
        // ========================================

        const exchange2Name =
            `EX_${uuid4}_${sourceEndpoint}_${uuid4}_${destinationEndpoint}`;

        document.getElementById("exchange2Name").textContent =
            exchange2Name;


        // ========================================
        // Notification Users
        // ========================================

        populateNotificationUsers(exchange.notificationUsers);
    }

    function populateNotificationUsers(users) {

        const container =
            document.getElementById("notifyUsersContainer");

        container.innerHTML = "";

        if (!users || users.length === 0) {
            container.innerHTML =
                `<p class="m-0 text-muted">No users were added</p>`;

            return;
        }

        users.forEach(user => {

            const userDiv = document.createElement("div");

            userDiv.className =
                "d-flex align-items-center justify-content-between mb-2";

            userDiv.innerHTML = `
            <p class="p-0 m-0">${user.email}</p>

            <button
                type="button"
                class="btn btn-outline-danger rounded-5"
                onclick="revokeUser(${user.id})">
                Revoke
            </button>
        `;

            container.appendChild(userDiv);
        });
    }
});
