$(document).ready(function(){

    $(window).on("scroll load", function () {
        if ($(this).scrollTop() > 50) {

            document.getElementById("btn-back-to-top").style.visibility = "visible";
        } else {
            document.getElementById("btn-back-to-top").style.visibility = "hidden";
        }
    });

    // scroll body to 0px on click
    $('#btn-back-to-top').click(function () {
        $('body,html').animate({
            scrollTop: 0
        }, 400);
        return false;
    });
});