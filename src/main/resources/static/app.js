const searchCountryInput = document.querySelector("#searchCountry");


document.addEventListener("DOMContentLoaded", () =>
{
    // Search input
    searchCountryInput.addEventListener("input", findByCountry);
    
    
    /* 
        Table sorting
    */
    let ascDir = false;
    const table = document.querySelector("table#countryList");
    const sortBtn = table.querySelectorAll("#th-sortable");
    
    // Crear icono (elemento) de direccion
    const dirIcon = document.createElement("SPAN");
    dirIcon.classList.add("ms-1");
    
    sortBtn.forEach(btn =>
    {
        btn.addEventListener("click", e =>
        {
            // cellIndex    = almacena su columna en la tabla
            // e.path[0]    = corresponde a sí mismo
            // e.path[1]    = corresponde al icon - accedemos a su parentElement (<td>)
            // || 0         = Asignar por default la columna 0 - evitar bug de undefined
            const column = e.path[0].cellIndex || e.path[1].cellIndex || 0; // Extraemos qué columna se ordenará
            const isInt = btn.getAttribute("data-type") === "int";
            
            // Ordenar tabla
            sortTableByColumn(table, column, ascDir, isInt);
            ascDir = !ascDir; // Cambianos la dirección de ordenamiento
            
            // Cambiar icono de direccion
            dirIcon.innerHTML = ascDir ? ("&darr;") : ("&uarr;");
            btn.appendChild(dirIcon); // Movemos el ícono al th que se está ordenando
        });
    });
});


function findByCountry()
{
    const text = searchCountryInput.value.trim();
    const exp = new RegExp(text, "i");
    const rows = document.querySelectorAll("table#countryList tbody tr"); // Seleccionar cada table row
    
    let count = 0;
    const caption = document.querySelector("#table-caption");
    
    rows.forEach(row =>
    {
        row.style.display = "none"; // Ocultar row por default
        
        const name = row.querySelector("#country-name").textContent.trim(); // Extraer el nombre del país
        if(name.replace(/\s/g, " ").search(exp) !== -1) // El nombre del país coincide con la búsqueda
        {
            row.style.display = "table-row"; // Mostrar tr
            count++;
        }
    });
    
    caption.textContent = (text) ? (`${count} result(s)`) : (`${count} countries`);
}

function sortTableByColumn(table, column, asc = true, isInt = true)
{
    const body = table.querySelector("tbody");
    const rows = Array.from(body.querySelectorAll("tr")); // Creamos un array de todos los tr en el tbody
    let sorted = null;
    
    if(isInt) // Verificamos si se está ordenando números
    {
        sorted = rows.sort((a, b) =>
        {
            const aVal = a.querySelector(`td:nth-child(${column + 1})`).textContent.replaceAll(",", "").trim(); // Extraemos el contenido de la columna
            const bVal = b.querySelector(`td:nth-child(${column + 1})`).textContent.replaceAll(",", "").trim(); //
            return asc ? (aVal - bVal) : (bVal - aVal); // Comparamos
        });
    }
    else // Se está ordenando un string
    {
        const dir = asc ? 1 : -1;
        sorted = rows.sort((a, b) =>
        {
            const aVal = a.querySelector(`td:nth-child(${column + 1})`).textContent.trim(); // Extraemos el contenido de la columna
            const bVal = b.querySelector(`td:nth-child(${column + 1})`).textContent.trim(); //
            return aVal > bVal ? (1 * dir) : (-1 * dir); // Comparamos
        });
    }
    
    // Vaciamos el tbody
    while(body.firstChild)
    {
        body.removeChild(body.firstChild);
    }
    
    // Anexamos una copia de la variable "sorted" al tbody
    body.append(...sorted);
}

