## Data Models
**Bold** indicates that a field is an index while *italics* indicates that the field is optional.

#### ride
| Field                    | Type                      | Description       |
|--------------------------|---------------------------|-------------------|
| route-title              | string                    |                   |
| route-description        | string                    |                   |
| start-timestamp          | double                    | epoch seconds UTC | 
| end-timestamp            | double                    | epoch seconds UTC | 
| total-time               | double                    | seconds           |
| total-distance           | double                    | meters            |
| field-statistic-sets     | list[field-statistic-set] |                   |
| sample-points            | list[sample-point]        |                   |
| geo-notes                | list[geo-note]            |                   |
| original-file            | bytes                     | lz4 compressed    |
| original-file-size-bytes | integer                   |                   |

#### geo-note
| Field       | Type      | Description |     
|-------------|-----------|-------------| 
| latitude    | double    | degrees     | 
| longitude   | double    | degrees     | 
| description | string    |             |
| photo       | string    | url         |

### sample-point
| Field          | Type                | Description       |
|----------------|---------------------|-------------------|
| timestamp      | double              | epoch seconds UTC | 
| latitude       | double              | degrees           |
| longitude      | double              | degrees           |
| speed          | double              | meters/second     |
| elevation      | double              | meters            |
| elevation-gain | double              | meters/second     |   
| acceleration   | double              | m/s<sup>2</sup>   |
| grade          | double              | percent           |
| total-distance | double              | meters            |
| total-time     | double              | seconds           |
| other-fields   | map[string, double] | key, value pairs  |

### field-statistic-set
| Field         | Type         |
|---------------|--------------|
| field-name    | string       |
| min           | double       |
| max           | double       |
| mean          | double       |
| median        | double       |
| range         | double       |
| variance      | double       |


<!--
| mode          | list[double] |   
-->

