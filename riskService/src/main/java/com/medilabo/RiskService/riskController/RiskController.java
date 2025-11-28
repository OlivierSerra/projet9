    package com.medilabo.RiskService.riskController;

    import com.medilabo.RiskService.model.RiskResult;

    import com.medilabo.RiskService.service.RiskService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/riskService")
    public class RiskController {

        private final RiskService riskService;

        public RiskController(RiskService riskService) {
            this.riskService = riskService;
        }

        // =======================
        // CAS 1 : avec l'ID patient
        // =======================
        @GetMapping("/patients/{patientId}")
        public ResponseEntity<RiskResult> assessByPatientId(@PathVariable String patientId) {
            RiskResult result = riskService.assess(patientId);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        }

        // =======================
        // CAS 2 : avec nom + prénom
        // =======================

        @GetMapping("/patients")
            public ResponseEntity<RiskResult> assessByNames(
                    @RequestParam("lastName") String lastName,
                    @RequestParam("firstName") String firstName) {

                RiskResult result = riskService.assessRiskByNames(lastName, firstName);
                if (result == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(result);
            }

            @GetMapping
            public String riskGateway() {
                return "Risk service is working";
            }
        }

