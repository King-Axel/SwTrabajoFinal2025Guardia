#language: es
Característica: Modulo de emergencias

  En esta caracteristica se presentan escenarios relacionados al registro de ingresos de pacientes a la guardia,
  respetando su nivel de criticidad y el horario de llegada a la guardia.

  Como enfermera
  Quiero poder registrar las admisiones de los pacientes a urgencias
  Para determinar que pacientes tienen mayor prioridad de atención

  Antecedentes:
    Dado que la siguiente enfermera esta registrada y autenticada:
      |Apellido|Nombre|
      |Fernandez|Lucia|
    Y los siguientes pacientes estan registrados:
      |Cuil         |Apellido |Nombre   |
      |20-34567891-3|Gonzalez |Sofia    |
      |27-34567890-7|Romero   |Benjamin |
      |20-42345678-9|Sanchez  |Agustina |
      |27-40123456-0|Diaz     |Joaquin  |


  Escenario: Ingreso de un paciente ya registrado, cuando la cola de atencion esta vacia
    Cuando llega el paciente:
      |Cuil         |Apellido|Nombre  |Informe                               |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|
      |27-34567890-7|Romero  |Benjamin|Trauma severo por accidente de trafico|Critica            |39.5       |135                |28                     |180                 |110                  |
    Entonces la cola de espera con cuils, ordenada por criticidad y hora de llegada, se ordena de la siguiente manera:
      |27-34567890-7|

s
  Escenario: Ingreso de un paciente no registrado, cuando la cola de atencion esta vacia
    Cuando llega el paciente:
      |Cuil         |Apellido|Nombre |Informe                           |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|
      |27-55667788-9|Torres  |Martina|Dolor abdominal intenso y vomitos |Urgencia           |38.2       |110                |24                     |130                 |85                   |
    Entonces el paciente "27-55667788-9" estara registrado:
    Y la cola de espera con cuils, ordenada por criticidad y hora de llegada, se ordena de la siguiente manera:
      |27-55667788-9|


  Escenario: Ingreso de un paciente de bajo nivel de emergencia, y luego un paciente con mayor nivel de emergencia
    Cuando llegan los pacientes:
      |Cuil         |Apellido|Nombre  |Informe                               |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|
      |27-55667788-9|Torres  |Martina |Dolor abdominal intenso y vomitos     |Urgencia           |38.2       |110                |24                     |130                 |85                   |
      |27-34567890-7|Romero  |Benjamin|Trauma severo por accidente de trafico|Critica            |39.5       |135                |28                     |180                 |110                  |
    Entonces la cola de espera con cuils, ordenada por criticidad y hora de llegada, se ordena de la siguiente manera:
      |27-34567890-7|
      |27-55667788-9|


  Escenario: Ingreso de cuatro pacientes, dos con un nivel de emergencia y dos con otro nivel de emergencia
    Cuando llegan los pacientes:
      |Cuil         |Apellido|Nombre  |Informe                           |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|
      |20-42345678-9|Sanchez |Agustina |Dolor toracico intenso           |Critica            |39.2       |140                |30                     |180                 |110                  |
      |27-40123456-0|Diaz    |Joaquin  |Fractura de pierna tras accidente|Urgencia           |37.8       |95                 |20                     |125                 |80                   |
      |27-55667788-9|Torres  |Martina |Dolor abdominal intenso y vomitos |Critica            |38.2       |110                |24                     |130                 |85                   |
      |27-34567890-7|Romero  |Benjamin|Dolor abdominal intenso y vomitos |Urgencia           |38.2       |110                |24                     |130                 |85                   |
    Entonces la cola de espera con cuils, ordenada por criticidad y hora de llegada, se ordena de la siguiente manera:
      |20-42345678-9|
      |27-55667788-9|
      |27-40123456-0|
      |27-34567890-7|


    Escenario: Ingreso de un paciente valores de frecuencia cardiaca negativos
      Cuando llega el paciente:
        |Cuil         |Apellido|Nombre|Informe               |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |-80                |15                     |180                 |110                  |
      Entonces se emite el siguiente mensaje:
        |Error: La frecuencia cardiaca y la frecuencia respiratoria no pueden ser valores negativos|

    Escenario: Ingreso de un paciente valores de frecuencia respiratoria negativos
      Cuando llega el paciente:
        |Cuil         |Apellido|Nombre|Informe               |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |80                 |-15                    |180                 |110                  |
      Entonces se emite el siguiente mensaje:
        |Error: La frecuencia cardiaca y la frecuencia respiratoria no pueden ser valores negativos|

    Esquema del escenario: Ingreso de un paciente con datos incompletos
      Cuando llega el paciente:
        |Cuil         |Apellido  |Nombre  |Informe  |Nivel de Emergencia  |Temperatura  |Frecuencia Cardiaca  |Frecuencia Respiratoria  |Frecuencia Sistolica  |Frecuencia Diastolica  |
        |<Cuil>       |<Apellido>|<Nombre>|<Informe>|<Nivel de Emergencia>|<Temperatura>|<Frecuencia Cardiaca>|<Frecuencia Respiratoria>|<Frecuencia Sistolica>|<Frecuencia Diastolica>|
      Entonces se emite el siguiente mensaje:
        |Error: falta el dato <Dato Faltante>|
      Ejemplos:
        |Cuil         |Apellido|Nombre|Informe               |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca|Frecuencia Respiratoria|Frecuencia Sistolica|Frecuencia Diastolica|Dato Faltante          |
        |             |MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |80                 | 15                    |180                 |110                  |Cuil                   |
        |20-42345678-9|        |Jony  |Dolor toracico intenso|Critica            |37         |80                 | 15                    |180                 |110                  |Apellido               |
        |20-42345678-9|MeLavo  |      |Dolor toracico intenso|Critica            |37         |80                 | 15                    |180                 |110                  |Nombre                 |
        |20-42345678-9|MeLavo  |Jony  |                      |Critica            |37         |80                 | 15                    |180                 |110                  |Informe                |
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|                   |37         |80                 | 15                    |180                 |110                  |Nivel de Emergencia    |
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |           |80                 | 15                    |180                 |110                  |Temperatura            |
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |                   | 15                    |180                 |110                  |Frecuencia Cardiaca    |
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |80                 |                       |180                 |110                  |Frecuencia Respiratoria|
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |80                 | 15                    |                    |110                  |Frecuencia Sistolica   |
        |20-42345678-9|MeLavo  |Jony  |Dolor toracico intenso|Critica            |37         |80                 | 15                    |180                 |                     |Frecuencia Diastolica  |

