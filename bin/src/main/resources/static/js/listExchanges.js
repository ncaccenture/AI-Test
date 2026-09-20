document.addEventListener("DOMContentLoaded", () => {
    loadExchanges();
});

function createExchangeRow(exchange, index) {

    const row = document.createElement("tr");

    const sourceQuartet = exchange.sourceQuartet;
    const destinationQuartet = exchange.destinationQuartet;

    row.innerHTML = `

        <th scope="row">
            ${index}
        </th>

        <td style="width: 80%">

            <div class="d-flex flex-column">

                <!-- SOURCE -->
                <div
                    class="d-flex align-items-center overflow-hidden
                           border border-bottom-0 border-2 rounded-top-4">

                    <div
                        class="bg-light px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold text-primary">
                            Source
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${sourceQuartet.compound}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${sourceQuartet.study}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${sourceQuartet.department}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${sourceQuartet.partner}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2"
                        style="width: 160px">

                        <span class="fw-semibold text-uppercase">
                            ${exchange.sourceEndpoint}
                        </span>

                    </div>

                </div>


                <!-- DESTINATION -->
                <div
                    class="d-flex overflow-hidden align-items-center
                           border border-2 rounded-bottom-4 bg-lightgrey">

                    <div
                        class="bg-light px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold text-success">
                            Destination
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${destinationQuartet.compound}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${destinationQuartet.study}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${destinationQuartet.department}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2 border-end border-3"
                        style="width: 160px">

                        <span class="fw-semibold">
                            ${destinationQuartet.partner}
                        </span>

                    </div>

                    <div
                        class="d-flex align-items-center justify-content-center
                               px-4 py-2"
                        style="width: 160px">

                        <span class="fw-semibold text-uppercase">
                            ${exchange.destinationEndpoint}
                        </span>

                    </div>

                </div>

            </div>

        </td>

        <td class="text-center">

            <button
                type="button"
                class="btn btn-outline-primary btn-sm px-3"
                onclick="viewExchange('${exchange.id}')">

                View

            </button>

        </td>
    `;

    return row;
}

const exchangeDataLoader = document.getElementById("exchangeDataLoader");
const exchangeDataTable = document.getElementById("exchangeDataTable");

async function loadExchanges() {

    const tableBody = document.getElementById("exchangeTableBody");

    try {

        const response = await fetch("/api/exchange/getAll");
        const result = await response.json();

        if (!response.ok) {
            throw new Error(
                result.message || "Failed to fetch exchanges"
            );
        }
        const exchanges = result.data;
        tableBody.innerHTML = "";

        if (!exchanges || exchanges.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="3"
                        class="text-center text-muted py-4">
                        No exchanges found
                    </td>
                </tr>
            `;

            return;
        }

        exchanges.forEach((exchange, index) => {
            window.exchangeData = window.exchangeData || {};
            window.exchangeData[exchange.id] = exchange;
            tableBody.appendChild(
                createExchangeRow(exchange, index + 1)
            );
        });

    } catch (error) {

        console.error("Error loading exchanges:", error);

        tableBody.innerHTML = `
            <tr>
                <td colspan="3"
                    class="text-center text-danger py-4">
                    Failed to load exchanges
                </td>
            </tr>
        `;
    }finally {
        exchangeDataLoader.classList.add("d-none");
        exchangeDataTable.classList.remove("d-none");
    }
}

function viewExchange(exchangeId) {

    const exchange = window.exchangeData[exchangeId];

    if (!exchange) {
        console.error("Exchange not found:", exchangeId);
        return;
    }

    const quartet = exchange.sourceQuartet;

    const params = new URLSearchParams({
        compound: quartet.compound,
        study: quartet.study,
        department: quartet.department,
        partner: quartet.partner,
        sourceEndpoint: exchange.sourceEndpoint,
        destinationEndpoint: exchange.destinationEndpoint
    });

    window.location.href =
        `/manageExchange?${params.toString()}`;
}

