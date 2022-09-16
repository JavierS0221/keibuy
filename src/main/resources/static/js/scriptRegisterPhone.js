var utilsPath = "/plugin/intl-tel-input-17.0.18/js/utils.js"
const phoneInputField = document.querySelector("#telephone");
const telephoneWarnMsg = document.querySelector("#telephone-warn-msg");

const phoneInput = window.intlTelInput(phoneInputField, {
    initialCountry: "auto",
    preferredCountries: ["us", "uy", "ar"],
    geoIpLookup: getIp,
    utilsScript: utilsPath,
});

function getIp(callback) {
    fetch('https://ipinfo.io/json?token=6c29658814848f', {headers: {'Accept': 'application/json'}})
        .then((resp) => resp.json())
        .catch(() => {
            return {
                country: 'uy',
            };
        })
        .then((resp) => callback(resp.country));
}

var errorMap = ["Invalid number", "Invalid country code", "Too short", "Too long", "Invalid number"];

phoneInputField.addEventListener("keypress", function (e) {
    var charCode = e.keyCode;
    if (charCode == 32 || charCode == 43) return;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        e.preventDefault();
    }
});


phoneInputField.addEventListener("keyup", function (event) {
    if (!phoneInput.isValidNumber()) {
        var errorCode = phoneInput.getValidationError();
        // alert(errorCode + " " + errorMap[errorCode])
        this.setCustomValidity(errorMap[errorCode]);
        this.classList.remove("was-validated");
        telephoneWarnMsg.innerHTML = errorMap[errorCode]
    } else {
        this.setCustomValidity("");
        this.classList.add("was-validated");
        telephoneWarnMsg.innerHTML = "";
    }
});