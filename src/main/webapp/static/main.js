
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