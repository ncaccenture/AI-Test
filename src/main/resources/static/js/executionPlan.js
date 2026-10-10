let executionPlan = null;

function buildDisplayData(data) {
  const buildDisplayData = {
    partnerType: data.partnerType,
    canProceed: data.canProceed,
    exchange: data.exchange,
    quartet: data.quartet,
    steps: data.steps,
    summary: data.summary,
    users: data.users,
  };
  displayAnalysis(buildDisplayData);
}

function displayAnalysis(data) {
  /*
   * Enable execute button
   */

  const executeBtn = document.getElementById("executeBtn");

  executeBtn.disabled = !data.canProceed;

  /* SUMMARY*/

  document.getElementById("summarySection").classList.remove("d-none");
  document.getElementById("summaryText").textContent = data.summary;

  /*
   * QUARTET
   */

  if (data.quartet) {
    document.getElementById("quartetSection").classList.remove("d-none");
    document.getElementById("quartetCompound").textContent =
      data.quartet.compound || "-";
    document.getElementById("quartetBusinessUnit").textContent =
      data.quartet.businessUnit || "-";
    document.getElementById("quartetStudy").textContent =
      data.quartet.study || "-";
    document.getElementById("quartetDepartment").textContent =
      data.quartet.department || "-";
    document.getElementById("quartetPartner").textContent =
      data.quartet.partner || "-";

    const quartetStatus = document.getElementById("quartetStatus");

    if (data.quartet.exists) {
      quartetStatus.textContent = "EXISTS";
      quartetStatus.className = "badge text-bg-success";
    } else {
      quartetStatus.textContent = data.quartet.willCreate
        ? "WILL BE CREATED"
        : "NOT FOUND";
      quartetStatus.className = "badge text-bg-warning";
    }
  }

  /*
   * USERS
   */

  if (data.users && data.users.length > 0) {
    document.getElementById("usersSection").classList.remove("d-none");

    const tbody = document.getElementById("usersTableBody");

    tbody.innerHTML = "";

    data.users.forEach((user, index) => {
      const row = document.createElement("tr");

      const fullName = `${user.firstName || ""} ${user.lastName || ""}`.trim();

      let statusBadge;

      if (user.exists) {
        statusBadge = `<span class="badge text-bg-success">
                        EXISTS
                    </span>`;
      } else {
        statusBadge = `<span class="badge text-bg-warning">
                        WILL BE CREATED
                    </span>`;
      }

      const actionBadge =
        user.action === "CREATE"
          ? `<span class="badge text-bg-primary">
                        CREATE
                       </span>`
          : `<span class="badge text-bg-secondary">
                        ${user.action || "-"}
                       </span>`;

      row.innerHTML = `

                <td>
                    ${index + 1}
                </td>

                <td class="fw-semibold">
                    ${fullName || "-"}
                </td>

                <td>
                    ${user.email || "-"}
                </td>

                <td>
                    ${statusBadge}
                </td>

                <td>
                    ${actionBadge}
                </td>

                <td class="text-center">

                    ${
                      user.notification
                        ? `<span class="text-success fw-bold">
                            ✓
                           </span>`
                        : `<span class="text-muted">
                            —
                           </span>`
                    }

                </td>

                <td class="text-center">

                    ${
                      user.sftpCredentials
                        ? `<span class="text-success fw-bold">
                            ✓
                           </span>`
                        : `<span class="text-muted">
                            —
                           </span>`
                    }

                </td>

            `;

      tbody.appendChild(row);
    });
  }

  /*
   * EXCHANGE
   */

  if (data.exchange) {
    document.getElementById("exchangeSection").classList.remove("d-none");

    document.getElementById("partnerType").textContent =
      data.partnerType || "-";
    document.getElementById("partnerType2").textContent =
      data.partnerType || "-";

    let partnerType = data.partnerType || "-";
    let sourceExchangeType = "";
    let sourceExchangeEndpoint = "";
    if (partnerType === "SFTP") {
      sourceExchangeType = "Mina";
      sourceExchangeEndpoint = "Mina";
    } else if (partnerType === "Internal") {
      sourceExchangeType = "Other";
      sourceExchangeEndpoint = "Internal";
    } else if (partnerType === "RAVE") {
      sourceExchangeType = "Other";
      sourceExchangeEndpoint = "Rave";
    }
    document.getElementById("sourceExchangeType").textContent =
      sourceExchangeType;
    document.getElementById("sourceExchangeEndpoint").textContent =
      sourceExchangeEndpoint;

    document.getElementById("destinationExchangeType").textContent =
      "ECCE (default)";
    document.getElementById("destinationExchangeEndpoint").textContent =
      "ECCE (default)";

    if(partnerType === "RAVE") {
      document.getElementById("sourceExchangeInboundPath").textContent = data.exchange?.inboundPath || "-";
      document.getElementById("sourceExchangeOutboundPath").textContent = data.exchange?.outboundPath || "-";
      document.getElementById("destinationExchangeFileNameSuffix").textContent = data.exchange?.destinationFileNameSuffix || "-";
      document.getElementById("pathDetails").classList.remove("d-none");
    }

    const exchangeStatus = document.getElementById("exchangeStatus");

    if (data.exchange.exists) {
      exchangeStatus.textContent = "EXISTS";
      exchangeStatus.className = "badge text-bg-success";
    } else {
      exchangeStatus.textContent = data.exchange.willCreate
        ? "WILL BE CREATED"
        : "NOT FOUND";
      exchangeStatus.className = "badge text-bg-warning";
    }
  }

  /*
   * EXECUTION STEPS
   */

  if (data.steps && data.steps.length > 0) {
    document.getElementById("stepsSection").classList.remove("d-none");
    const container = document.getElementById("stepsContainer");

    container.innerHTML = "";

    data.steps.forEach((step) => {
      const stepDiv = document.createElement("div");

      stepDiv.className = "card mb-3";

      stepDiv.innerHTML = `

                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div
                            class="rounded-circle bg-primary text-white
                                   d-flex align-items-center justify-content-center
                                   me-3"
                            style="width:40px;height:40px">
                            ${step.step}
                        </div>

                        <div class="flex-grow-1">

                            <h6 class="mb-1 fw-bold">
                                ${step.title}
                            </h6>

                            <p class="mb-0 text-muted">
                                ${step.description}
                            </p>
                        </div>

                        <span class="badge text-bg-secondary">
                            ${step.status}
                        </span>
                    </div>
                </div>
            `;
      container.appendChild(stepDiv);
    });
  }
}

const executionPlanLoader = document.getElementById("executionPlanLoader");
const wholeContainer = document.getElementById("whole-container");

async function analyzeExecutionPlan(extractedData) {
  executionPlanLoader.classList.remove("d-none");

  try {
    const response = await fetch("/api/automation/execution-plan", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(extractedData),
    });

    const result = await response.json();
    console.log("Execution Plan Result:", result);

    if (!response.ok) {
      throw new Error(result.message || "Failed to create execution plan");
    }

    executionPlan = result;
    buildDisplayData(result);

    wholeContainer.classList.remove("d-none");
  } catch (error) {
    console.error("Error analyzing:", error);
  } finally {
    executionPlanLoader.classList.add("d-none");
  }
}

function showModal(message) {
  document.getElementById("executionPlanResponseModalBody").textContent =
    message;
  const modal = new bootstrap.Modal(
    document.getElementById("executionResponseModal"),
  );
  modal.show();
}

const executeBtn = document.getElementById("executeBtn");
executeBtn.addEventListener("click", executeAutomation);

async function executeAutomation() {
  if (!executionPlan) {
    return;
  }

  const executeBtn = document.getElementById("executeBtn");

  executeBtn.disabled = true;

  document.getElementById("executionSection").classList.remove("d-none");

  const stepsContainer = document.getElementById("executionSteps");

  stepsContainer.innerHTML = "";

  try {
    const response = await fetch("/api/automation/execute", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(executionPlan),
    });

    const result = await response.json();
    displayExecutionResult(result);
  } catch (error) {
    console.error(error);
    showModal("Automation failed: " + error.message, "danger");
  } finally {
    executeBtn.disabled = false;
  }
}

function displayExecutionResult(result) {
  const container = document.getElementById("executionSteps");

  container.innerHTML = "";

  result.steps.forEach((step) => {
    const stepElement = document.createElement("div");

    stepElement.className = "d-flex align-items-start mb-3";

    stepElement.innerHTML = `
            <div class="me-3">

                <span class="badge bg-success rounded-circle"
                      style="
                          width: 32px;
                          height: 32px;
                          display: flex;
                          align-items: center;
                          justify-content: center;
                      ">

                    ✓

                </span>

            </div>

            <div>

                <div class="fw-bold">
                    Step ${step.step}: ${step.title}
                </div>

                <div class="text-muted">
                    ${step.description}
                </div>

            </div>
        `;

    container.appendChild(stepElement);
  });

  if (result.success) {
    showModal(result.message, "success");
  } else {
    showModal(result.message, "danger");
  }
}
