// For Filters
document.addEventListener("DOMContentLoaded", function () {
    var filterBtn = document.getElementById("filter-btn");
    var btnTxt = document.getElementById("btn-txt");
    var filterAngle = document.getElementById("filter-angle");

    var count = 0,
        count2 = 0;

    filterBtn.addEventListener("click", function () {
        count++;
        if (count % 2 != 0) {
            filterAngle.classList.remove("fa-angle-left");
            filterAngle.classList.add("fa-angle-right");
            btnTxt.innerText = "Show filters";
        } else {
            filterAngle.classList.remove("fa-angle-right");
            filterAngle.classList.add("fa-angle-left");
            btnTxt.innerText = "Hide filters";
        }
    });

    // For changing NavBar-Toggler-Icon
    var icon = document.getElementById("icon");

    function chnageIcon() {
        count2++;
        if (count2 % 2 != 0) {
            icon.innerText = "";
            icon.innerHTML =
                '<span class="far fa-times-circle" style="width:100%"></span>';
            icon.style.paddingTop = "5px";
            icon.style.paddingBottom = "5px";
            icon.style.fontSize = "1.8rem";
        } else {
            icon.innerText = "";
            icon.innerHTML = '<span class="navbar-toggler-icon"></span>';
            icon.style.paddingTop = "5px";
            icon.style.paddingBottom = "5px";
            icon.style.fontSize = "1.2rem";
        }
    }

    // Showing tooltip for AVAILABLE COLORS
    $(function () {
        $('[data-tooltip="tooltip"]').tooltip();
    });

});

// Deny drag image
$('.deny-drag').on('dragstart', function(event) { event.preventDefault(); });