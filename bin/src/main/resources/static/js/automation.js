const formFile = document.getElementById("formFile");
const processFormBtn = document.getElementById("processFormBtn");

const selectedFile = document.getElementById("selectedFile");
const fileName = document.getElementById("fileName");

const imagePreviewContainer =
    document.getElementById("imagePreviewContainer");

const imagePreview =
    document.getElementById("imagePreview");

const pdfInfo =
    document.getElementById("pdfInfo");

let analyzeApiResponse;
/*
* File selected
*/
formFile.addEventListener("change", () => {

    const file = formFile.files[0];

    // Reset UI
    imagePreviewContainer.classList.add("d-none");
    pdfInfo.classList.add("d-none");

    if (!file) {

        selectedFile.classList.add("d-none");
        processFormBtn.disabled = true;

        return;
    }


    // Show filename
    fileName.textContent = file.name;

    selectedFile.classList.remove("d-none");

    processFormBtn.disabled = false;


    /*
     * Image preview
     */
    if (file.type.startsWith("image/")) {

        const reader = new FileReader();

        reader.onload = function (event) {

            imagePreview.src = event.target.result;

            imagePreviewContainer.classList.remove("d-none");

        };

        reader.readAsDataURL(file);
    }


    /*
     * PDF
     */
    else if (file.type === "application/pdf") {

        pdfInfo.classList.remove("d-none");

    }

});


/*
* Process Form
*/
processFormBtn.addEventListener("click", async () => {

    const file = formFile.files[0];

    if (!file) {
        return;
    }

    processFormBtn.disabled = true;
    processFormBtn.textContent = "Processing...";

    try {

        const formData = new FormData();

        formData.append("file", file);


        const response = await fetch(
            "/api/automation/extract-pdf",
            {
                method: "POST",
                body: formData
            }
        );

        const result = await response.json();
        console.log("/api/automation/extract-pdf - result", result)

        if (!response.ok) {
            throw new Error(
                result.message || "Something went wrong while processing the form."
            );
        }
        analyzeApiResponse = result;

        document.getElementById("responseModalBody").textContent = "Form analyzed successfully. Click on Proceed to make execution plan";
        const modal = new bootstrap.Modal(document.getElementById("responseModal"));
        modal.show();

    } catch (error) {

        console.error(error);
        document.getElementById("errorModalBody").textContent = error.message;
        const errorModal = new bootstrap.Modal(document.getElementById("errorModal"));
        errorModal.show();

    } finally {
        processFormBtn.disabled = false;
        processFormBtn.textContent = "Process Form";
    }

});

const proceedBtn = document.getElementById("proceedBtn");

proceedBtn.addEventListener("click", async () => {

    await analyzeExecutionPlan(analyzeApiResponse);

});

