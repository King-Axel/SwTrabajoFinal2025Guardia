#language: es
Característica: Módulo de reclamo de paciente

  Como médico
  Quiero reclamar el próximo paciente que debe ser atendido
  Para sacarlo de la lista de espera y poder registrar un informe de atención
  # Criterios: cambia de PENDIENTE a EN_PROCESO y sale de la cola; error si la cola está vacía.

  Antecedentes:
    Dado que la siguiente enfermera esta registrada y autenticada en el sistema:
      | Apellido | Nombre |
      | Fernandez | Lucia |
    Y los siguientes pacientes estan registrados en el sistema:
      | Cuil          | Apellido | Nombre   |
      | 20-34567891-3 | Gonzalez | Sofia    |
      | 27-34567890-7 | Romero   | Benjamin |
      | 20-42345678-9 | Sanchez  | Agustina |
      | 27-40123456-0 | Diaz     | Joaquin  |

  Escenario: Médico reclama el próximo paciente cuando hay personas en lista de espera
    Y llegan los siguientes pacientes:
      | Cuil          | Apellido | Nombre   | Informe                               | Nivel de Emergencia | Temperatura | Frecuencia Cardiaca | Frecuencia Respiratoria | Frecuencia Sistolica | Frecuencia Diastolica |
      | 20-42345678-9 | Sanchez  | Agustina | Dolor toracico intenso                | Critica             | 39.2        | 140                 | 30                       | 180                   | 110                    |
      | 27-40123456-0 | Diaz     | Joaquin  | Fractura de pierna tras accidente     | Urgencia            | 37.8        | 95                  | 20                       | 125                   | 80                     |
    Y el siguiente médico está autenticado:
      | Apellido | Nombre |
      | Perez    | Ana    |
    Cuando el médico reclama el próximo paciente de la lista de espera
    Entonces el paciente reclamado es:
      | Cuil          |
      | 20-42345678-9 |
    Y el estado del ingreso de "20-42345678-9" es "EN_PROCESO"
    Y Y la cola de espera queda de la siguiente manera:
      | 27-40123456-0 |

  Escenario: Médico reclama cuando la lista de espera está vacía
    Y el siguiente médico está autenticado:
      | Apellido | Nombre |
      | Perez    | Ana    |
    Cuando el médico reclama el próximo paciente de la lista de espera
    Entonces se emite el siguiente mensaje de reclamo:
      | No hay ingresos en lista de espera |
