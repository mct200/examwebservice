package com.groupeisi.exam.web.rest;

import com.groupeisi.exam.domain.Historique;
import com.groupeisi.exam.repository.HistoriqueRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarDayFinderResource {

    private final HistoriqueRepository historiqueRepository;

    public CalendarDayFinderResource(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @GetMapping("/services/calendar/dayfinder")
    public ResponseEntity<Map<String, String>> findDayOfWeek(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayOfWeekString = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH);

        // Sauvegarder la date recherchée dans l'historique
        Historique historique = new Historique();
        historique.setDateRecherchee(date.toString()); // Conversion de LocalDate en String
        historique.setJourSemaine(dayOfWeekString);
        historiqueRepository.save(historique);

        Map<String, String> response = new HashMap<>();
        response.put("date", date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Formatage de LocalDate en String
        response.put("dayOfWeek", dayOfWeekString);

        return ResponseEntity.ok().body(response);
    }
}
