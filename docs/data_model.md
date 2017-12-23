## Data Models
**Bold** indicates that a field is an index while *italics* indicates that the field is optional.

#### ride
| Field                    | Type                      | Description       |
|--------------------------|---------------------------|-------------------|
| route_title              | string                    |                   |
| route_description        | string                    |                   |
| start_timestamp          | double                    | epoch seconds UTC | 
| end_timestamp            | double                    | epoch seconds UTC | 
| total_time               | double                    | seconds           |
| total_distance           | double                    | meters            |
| field_statistic_sets     | list[field_statistic_set] |                   |
| sample_points            | list[sample_point]        |                   |
| geo_notes                | list[geo_note]            |                   |
| original_file            | bytes                     | lz4 compressed    |
| original_file_size_bytes | integer                   |                   |

#### geo_note
| Field       | Type      | Description |     
|-------------|-----------|-------------| 
| latitude    | double    | degrees     | 
| longitude   | double    | degrees     | 
| description | string    |             |
| photo       | string    | url         |

### sample_point
| Field          | Type                | Description       |
|----------------|---------------------|-------------------|
| timestamp      | double              | epoch seconds UTC | 
| latitude       | double              | degrees           |
| longitude      | double              | degrees           |
| speed          | double              | meters/second     |
| elevation      | double              | meters            |
| elevation_gain | double              | meters/second     |   
| acceleration   | double              | m/s<sup>2</sup>   |
| grade          | double              | percent           |
| total_distance | double              | meters            |
| total_time     | double              | seconds           |
| other_fields   | map[string, double] | key, value pairs  |

### field_statistic_set
| Field         | Type         |
|---------------|--------------|
| field_name    | string       |
| min           | double       |
| max           | double       |
| mean          | double       |
| median        | double       |
| range         | double       |
| variance      | double       |


<!--
| mode          | list[double] |   
-->

