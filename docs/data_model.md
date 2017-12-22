## Data Models
**Bold** indicates that a field is an index while *italics* indicates that the field is optional.

#### ride
| Field                             | Type                      |
|-----------------------------------|---------------------------|
| route_title                       | string                    |
| route_description                 | string                    |
| start_timestamp_epoch_seconds_utc | double                    |
| end_timestamp_epoch_seconds_utc   | double                    |
| total_time_seconds                | double                    |
| total_distance                    | double                    |
| field_statistic_sets              | list[field_statistic_set] |
| sample_points                     | list[sample_point]        |
| geo_notes                         | list[geo_note]            |
| original_file_lz4_compressed      | bytes                     |
| original_file_size_bytes          | integer                   |

#### geo_note
| Field                 | Type      |    
|-----------------------|-----------|
| latitude_degrees      | double    |
| longitude_degrees     | double    |
| description           | string    |
| photo                 | url       |

### sample_point
| Field                             | Type              |
|-----------------------------------|-------------------|
| timestamp_epoch_seconds_utc       | double            |
| latitude_degrees                  | double            |
| longitude_degrees                 | double            |
| speed_meters_per_second           | double            |
| elevation_meters                  | double            |
| acceleration_meters_per_second    | double            |
| percent_grade                     | double            |
| total_distance_meters             | double            |
| total_time_seconds                | double            |
| other_fields                      | list[other_field] |

### other_field
| Field | Type   |
|-------|--------|
| name  | string |
| value | double |

### field_statistic_set
| Field         | Type   |
|---------------|---------------|
| field_name    | string        |
| min           | double        |
| max           | double        |
| mean          | double        |
| median        | double        |
| mode          | list[double]  |
| range         | double        |
| variance      | double        |


