
function collapse_sidebar() {
    const sidebar = document.getElementById("sidebar");
    let collapse_button = document.getElementById("collapse-sidebar");

    if (sidebar.classList.contains("wide")) {
        sidebar.classList.remove("wide");
        collapse_button.innerHTML = "&gt"; // ">"
        console.log("if: " + collapse_button.innerHTML);
    } else {
        sidebar.classList.add("wide");
        collapse_button.innerHTML = "&lt"; // "<"
        console.log("else: " + collapse_button.innerHTML);
    }


}

function changeacctype(element) {
    let newtype = element.selectedIndex;
    let dept = document.getElementById("dept-input");
    let major = document.getElementById("major-input");
    if (newtype === 1) {
        dept.classList.remove("hide");
        major.classList.add("hide");
    } else {
        major.classList.remove("hide");
        dept.classList.add("hide");
    }
}