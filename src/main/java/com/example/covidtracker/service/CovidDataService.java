package com.example.covidtracker.service;

import com.example.covidtracker.models.Country;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class CovidDataService 
{
    private static final String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    
    private List<Country> allStats = new ArrayList<>(); // Almacena todos los datos
    private int allNewCases = 0; // Almacena los nuevos casos en todo el mundo
    
    
    @PostConstruct // Ejecuta este codigo cuando la aplicacion se inicia
    @Scheduled(cron = "* * 1 * * *") // s:m:h:d:m:y - Ejecuta este código cada 1 hora
    public void fetchCovidData() throws IOException, InterruptedException
    {
        List<Country> newStats = new ArrayList<>(); // Almacena temporalmente los nuevos datos
        int countAllNewCases = 0; 
        
        
        // Enviar petición Http
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                                .newBuilder()
                                .uri(URI.create(COVID_DATA_URL))
                                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Leer csv
        StringReader in = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        
        //
        int i = -1;
        String lastName = ""; 
        
        for(CSVRecord record: records)
        {
            final int lastIdx = record.size() - 1; // Obtener último index (datos del último registro añadido / datos de la última fecha)
            
            final String name = record.get(1); // Obtener nombre del país
            final int totalCases = Integer.parseInt(record.get(lastIdx)); // Obtener el total de casos
            final int newCases = totalCases - Integer.parseInt(record.get(lastIdx - 1)); // Obtener la cantidad de casos nuevos (restar los casos del ultimo registro con los del anteultimo registro)
            
            // Hay países que aparecen varias veces en el registro
            // Para evitar duplicar países en la lista (newStats)
            // Se verifica que el país (nombre) sea diferente al país del registro/iteracion anterior
            if(!name.equals(lastName)) // Si es un nuevo país para añadir a la lista (el nombre en la actual iteracion es diferente al anterior)
            {
                i++; // Subir al siguiente index
                
                Country country = new Country();
                country.setName(name);
                country.setNewCases(0);
                country.setTotalCases(0);
                newStats.add(country); // Añadir el nuevo pais a la lista
            }
            
            Country tmp = newStats.get(i); // Acceder al país en la lista según el índice
            tmp.setNewCases(tmp.getNewCases() + newCases);
            tmp.setTotalCases(tmp.getTotalCases() + totalCases);
            
            countAllNewCases += newCases; // Sumar a la cantidad de casos nuevos globalmente
            lastName = name; // Guardar el último país
        }
        
        //
        this.allStats = newStats; // Actualizar la lista global con los nuevos datos
        this.allNewCases = countAllNewCases;
        
    }

    public List<Country> getAllStats() {
        return allStats;
    }
    
    public int getAllNewCases() {
        return allNewCases;
    }
}
