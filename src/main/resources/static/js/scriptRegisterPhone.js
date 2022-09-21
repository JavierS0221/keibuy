var utilsPath = "/plugin/intl-tel-input-17.0.19/js/utils.js"

const registerForm = document.querySelector("#register-form");

const phoneInputField = document.querySelector("#telephone");
const telephoneWarnMsg = document.querySelector("#telephone-warn-msg");

const registerBtn = document.querySelector("#register-btn");

const countryData = window.intlTelInputGlobals.getCountryData();
const countryDropdown = document.querySelector("#country")

let phoneInput = window.intlTelInput(phoneInputField, {
    initialCountry: "auto",
    preferredCountries: ["us", "uy", "ar"],
    geoIpLookup: getIp,
    utilsScript: utilsPath
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

var errorMap = ["Invalid number", "Invalid country code", "Invalid number [too short]", "Invalid number [too long]", "Invalid number"];

phoneInputField.addEventListener("keypress", function (e) {
    var charCode = e.keyCode;
    if (charCode === 32 || charCode === 43) return;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        e.preventDefault();
    }
});


phoneInputField.addEventListener("keyup", function (event) {
    if (!phoneInput.isValidNumber()) {
        var errorCode = phoneInput.getValidationError();
        // alert(errorCode + " " + errorMap[errorCode])
        this.setCustomValidity(errorMap[errorCode]);
        if(errorMap[errorCode] !== undefined && errorMap[errorCode] != null)
            telephoneWarnMsg.innerHTML = errorMap[errorCode]
        else if(phoneInputField.value.length > 0)
            telephoneWarnMsg.innerHTML = "Invalid number";
        else
            telephoneWarnMsg.innerHTML = "Telephone is required";
    } else {
        this.setCustomValidity("");
        telephoneWarnMsg.innerHTML = "";
    }
});

// populate the country dropdown
for (var i = 0; i < countryData.length; i++) {
    var country = countryData[i];
    var optionNode = document.createElement("option");
    optionNode.value = country.iso2;
    var textNode = document.createTextNode(country.name);
    optionNode.appendChild(textNode);
    countryDropdown.appendChild(optionNode);
}

// set it's initial value
countryDropdown.value = phoneInput.getSelectedCountryData().iso2;

// listen to the telephone input for changes
phoneInputField.addEventListener('countrychange', function(e) {
    countryDropdown.value = phoneInput.getSelectedCountryData().iso2;
});

function onSubmitRegister() {
   phoneInputField.value = phoneInput.getNumber();
};

// listen to the address dropdown for changes
countryDropdown.addEventListener('change', function() {
    phoneInput.setCountry(this.value);
});