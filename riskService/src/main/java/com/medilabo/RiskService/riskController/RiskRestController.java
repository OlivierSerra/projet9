package com.medilabo.RiskService.riskController;

import com.medilabo.RiskService.dto.RiskReportDto;
import com.medilabo.RiskService.service.RiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk")
public class RiskRestController {
        private final RiskService riskService;

        public RiskRestController(RiskService riskService) {
            this.riskService = riskService;
        }

        // GET /risk/{patientId}
        @GetMapping("/{patientId}")
        public ResponseEntity<RiskReportDto> getRisk(@PathVariable Long patientId) {
            RiskReportDto report = riskService.assessRisk(patientId);
            return ResponseEntity.ok(report);
        }

}
