import pdfplumber
import json
import re
import sys


PDF_PATH = "demoform1.pdf"


# ============================================================
# Extract page 1
# ============================================================

def extract_page_text(pdf_path):

    with pdfplumber.open(pdf_path) as pdf:
        return pdf.pages[0].extract_text()


# ============================================================
# Extract form data
# ============================================================

def extract_form_data(text):

    data = {
        "partnerAction": "",
        "businessUnit": "",
        "department": "",
        "compound": "",
        "study": "",
        "partnerType": "",
        "partnerCompanyName": "",
        "partnerIpRange": "",
        "users": [],
        "partnerContacts": []
    }

    # ========================================================
    # 1. Business Unit + Department
    # ========================================================

    match = re.search(
        r"Business Unit:\s*Dept:\s*\n([^\n]+)",
        text
    )

    if match:

        values = match.group(1).split()

        if len(values) >= 1:
            data["businessUnit"] = values[0]

        if len(values) >= 2:
            data["department"] = values[1]


    # ========================================================
    # 2. Compound + Study
    # ========================================================

    match = re.search(
        r"Compound:\s*(\S+)\s+Study:\s*(\S+)",
        text
    )

    if match:

        data["compound"] = match.group(1)
        data["study"] = match.group(2)


    # ========================================================
    # 3.1 Partner Action
    # ========================================================
    match = re.search(
        r"Partner Action:\s*(Add|Delete|Update)",
        text,
        re.IGNORECASE
    )

    if match:
        partner_action = match.group(1).strip()
        data["partnerAction"] = partner_action
    
    # ========================================================
    # 3.2 Partner Type
    # ========================================================
    
    match = re.search(
        r"Partner Type:\s*(.*?)(?=\s*Partner Action:)",
        text,
        re.DOTALL
    )
    
    if match:
        raw_partner_type = match.group(1).strip()
        partner_type = raw_partner_type.replace(" ", "")
        partner_type = partner_type.replace("*", "")
        data["partnerType"] = partner_type


    # ========================================================
    # 4. Partner Company
    # ========================================================

    match = re.search(
        r"Partner Company Name:\s*\n([^\n]+)",
        text
    )

    if match:

        data["partnerCompanyName"] = match.group(1).strip()


    # ========================================================
    # 5. Partner IP Range
    # ========================================================

    match = re.search(
        r"Partner IP Range\s*\(If External\):\s*\n([^\n]+)",
        text
    )

    if match:

        data["partnerIpRange"] = match.group(1).strip()


    # ========================================================
    # 6. Users
    # ========================================================

    user_section_match = re.search(
        r"Action Study Owner Name Eisai ID Phone # Email Notify"
        r"(.*?)"
        r"B\. Partner Company:",
        text,
        re.DOTALL
    )

    if user_section_match:

        user_section = user_section_match.group(1)

        study_owners = [
            "Biostats Primary",
            "Biostats Backup",
            "Dataops Primary",
            "Dataops Backup"
        ]

        actions = re.findall(
            r"\b(No Change|Add|Remove)\b",
            user_section
        )

        for i, owner in enumerate(study_owners):

            action = actions[i] if i < len(actions) else ""

            data["users"].append({
                "action": action,
                "studyOwner": owner,
                "name": "",
                "eisaiId": "",
                "phone": "",
                "email": "",
                "notify": False
            })


    # ========================================================
    # 7. Partner Contacts
    # ========================================================

    # ========================================================
    # 7. Partner Contacts
    # ========================================================

    contact_section_match = re.search(
        r"# Action Contact Name Contact Email Contact Ph# Notification/SFTP Credentials"
        r"(.*?)"
        r"\*Add attachment for bulk requests",
        text,
        re.DOTALL
    )

    if contact_section_match:

        contact_section = contact_section_match.group(1)

        # ----------------------------------------------------
        # Find each contact row separately
        # ----------------------------------------------------

        rows = re.findall(
            r"(\d+)\.\s*(.*?)(?=\n\d+\.|\Z)",
            contact_section,
            re.DOTALL
        )

        for row_number, row in rows:

            row = " ".join(row.split())

            # print( f"DEBUG CONTACT ROW {row_number}: {row}", file=sys.stderr)

            # ------------------------------------------------
            # Action
            # ------------------------------------------------

            action_match = re.search(
                r"\b(Add|No Change|Remove)\b",
                row,
                re.IGNORECASE
            )

            if not action_match:
                continue

            action = action_match.group(1)


            # ------------------------------------------------
            # Email
            # ------------------------------------------------

            email_match = re.search(
                r"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}",
                row
            )

            if not email_match:
                continue

            email = email_match.group(0)

            # ------------------------------------------------
            # Clean email
            #
            # Handles:
            #
            # Vivian.kao@firmaclinical.comN/A
            #
            # -> Vivian.kao@firmaclinical.com
            # ------------------------------------------------

            email = re.sub(
                r"(N/A)+$",
                "",
                email,
                flags=re.IGNORECASE
            )


            # ------------------------------------------------
            # Name
            # ------------------------------------------------

            # Everything between action and email
            name_match = re.search(
                r"\b(Add|No Change|Remove)\s+"
                r"(.+?)\s+"
                r"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}",
                row,
                re.IGNORECASE
            )

            if name_match:

                full_name = name_match.group(2).strip()

                name_parts = full_name.split()

                first_name = name_parts[0] if len(name_parts) >= 1 else ""
                last_name = " ".join(name_parts[1:]) if len(name_parts) >= 2 else ""

            else:

                first_name = ""
                last_name = ""


            # ------------------------------------------------
            # Phone
            # ------------------------------------------------

            phone = ""

            # Everything after email
            after_email = row[email_match.end():]

            # Remove N/A
            after_email = re.sub(
                r"^N/A",
                "",
                after_email,
                flags=re.IGNORECASE
            ).strip()

            # If actual phone exists, try to extract it
            phone_match = re.search(
                r"\+?\d[\d\s().-]{6,}\d",
                after_email
            )

            if phone_match:
                phone = phone_match.group(0).strip()


            # ------------------------------------------------
            # SFTP Credentials
            # ------------------------------------------------

            sftp_credentials = bool(
                re.search(
                    r"SFTP Credentials and Notifications",
                    row,
                    re.IGNORECASE
                )
            )


            # ------------------------------------------------
            # Notification
            # ------------------------------------------------

            notification = sftp_credentials


            # ------------------------------------------------
            # Add to result
            # ------------------------------------------------

            data["partnerContacts"].append({

                "action": action,

                "firstName": first_name,

                "lastName": last_name,

                "email": email,

                "phone": phone,

                "notification": notification,

                "sftpCredentials": sftp_credentials
            })

    return data


if __name__ == "__main__":

    if len(sys.argv) < 2:
        print("Usage: py start.py <pdf-file>")
        sys.exit(1)

    pdf_path = sys.argv[1]

    try:

        text = extract_page_text(pdf_path)

        # Fix email + N/A issue
        text = re.sub(
            r"(@[A-Za-z0-9.-]+\.[A-Za-z]{2,})N/A",
            r"\1 N/A",
            text,
            flags=re.IGNORECASE
        )

        data = extract_form_data(text)

        # IMPORTANT: only JSON goes to stdout
        print(
            json.dumps(
                data,
                indent=2,
                ensure_ascii=False
            )
        )

    except Exception as e:

        print(
            json.dumps({
                "error": str(e)
            })
        )

        sys.exit(1)