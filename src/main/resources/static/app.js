const searchCountryInput = document.querySelector("#searchCountry");

document.addEventListener("DOMContentLoaded", () =>
{
    searchCountryInput.addEventListener("input", findByCountry);
});


function findByCountry()
{
    const exp = new RegExp(searchCountryInput.value, "i");
    const rows = document.querySelectorAll("table#countryList tbody tr"); // Seleccionar cada table row (país)
    
    rows.forEach(row =>
    {
        row.style.display = "none"; // Ocultar tr por default
        
        // row              = <tr>
        // childNodes[1]    = Primer <td> - nombre del país
        // textContent      = Contiene el nombre del país
        if(row.childNodes[1].textContent.replace(/\s/g, " ").search(exp) != -1) // El nombre del país coincide con la búsqueda
        {
            row.style.display = "table-row"; // Mostrar tr
        }
    });
}
