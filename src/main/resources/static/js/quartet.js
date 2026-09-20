document.getElementById("quartetForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const quartetData = {
        businessUnit: document.getElementById("businessUnit").value,
        category: document.getElementById("category").value,
        compound: document.getElementById("compound").value,
        study: document.getElementById("study").value,
        department: document.getElementById("department").value,
        partner: document.getElementById("partner").value
    };

    try {

        const response = await fetch("/api/quartet/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(quartetData)
        });

        const result = await response.json();

        document.getElementById("responseModalBody").textContent =
            result.message;

        const modal = new bootstrap.Modal(
            document.getElementById("responseModal")
        );

        modal.show();

    } catch (error) {

        console.error(error);

        document.getElementById("responseModalBody").textContent =
            "Something went wrong!";

        const modal = new bootstrap.Modal(
            document.getElementById("responseModal")
        );

        modal.show();
    }
});