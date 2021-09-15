// ---------horizontal-navbar-menu-----------
const tabsNewAnim = $('#navbar-4');
// const selectorNewAnim = $('#navbar-4').find('li').length;
//var selectorNewAnim = $(".tabs").find(".selector");
const activeItemNewAnim = tabsNewAnim.find('.active');
const activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
const itemPosNewAnimLeft = activeItemNewAnim.position();
$(".hori-selector").css({
    "left": itemPosNewAnimLeft.left + "px",
    "width": activeWidthNewAnimWidth + "px"
});

// $("#navbar-4").on("click","li",function(e){
//     console.log($(this));
//     $('#navbar-4 ul li').removeClass("active");
//     $(this).addClass('active');
//     const activeWidthNewAnimWidth = $(this).innerWidth();
//     const itemPosNewAnimLeft = $(this).position();
//     $(".hori-selector").css({
//         "left":itemPosNewAnimLeft.left + "px",
//         "width": activeWidthNewAnimWidth + "px"
//     });
// });


const current_herf = String(window.location);
$("#navbar-4 ul li a").each(function (index) {
    if (Object.is(String($(this)[0]), String(current_herf))) {
        $('#navbar-4 ul li').removeClass("active");
        $(this).parent().addClass('active');
        const activeWidthNewAnimWidth = $(this).innerWidth();
        const itemPosNewAnimLeft = $(this).position();
        $(".hori-selector").css({
            "left": itemPosNewAnimLeft.left + "px",
            "width": activeWidthNewAnimWidth + "px"
        });
    }
});

