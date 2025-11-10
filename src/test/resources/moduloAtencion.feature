Feature: Modulo de creacion de atencion

  En la siguiente caracteristica se presentan los informes de un paciente ingresado en urgencia
  que ha sido atendido por un medico

  Como medico
  Quiero registrar un informe de atencion de un paciente ingresado en urgencia que he reclamado
  Para dejar constancia de la atencion brindada a dicho paciente

  Background:
    Given que la siguiente medica esta autenticada y registrada
      |Apellido | Nomnbre|
      |Agustina | Bazan  |

  Scenario: La doctora registra el informe del paciente y se cambia el estado del paciente
      When la doctora registra el informe del paciente
      Then se emite el siguiente mensaje:
      |La atencion ha sido registrada correctamente.|

    Scenario: La doctora registra el informe del paciente pero omitio el informe
      When la doctora registra el informe del paciente sin el informe
      Then se emite el siguiente mensaje:
      |Error: El informe debe ser ingresado.|




