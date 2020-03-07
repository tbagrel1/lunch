# lunch
Do you eat with us?

## Bootstrap test database

```
CREATE DATABASE lunch;
CREATE USER lunch WITH PASSWORD 'lunch';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to lunch;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to lunch;
```

## Entity mapping

### `Input$Entity`

+ mapped to/from JSON
+ represents a new Entity that the client wants to insert
+ lives in `com.tbagrel1.lunch.core.models.input`
+ shared by both the API and the Android app

### `Output$Entity`
+ mapped to/from JSON
+ represents an entity displayed to the client consuming the JSON
+ lives in `com.tbagrel1.lunch.core.models.output`
+ shared by both the API and the Android app

### `$Entity`
+ mapped to/from the database entity
+ represents the entity saved in the database with JPA
+ lives in `com.tbagrel1.lunch.api.db.models`

### `$EntityRepository`
+ mapped to/from the database table associated to this entity
+ lives in `com.tbagrel1.lunch.api.db.repositories`

```
Input$Entity   ------->   $Entity   ------->   Output$Entity
       $Entity.fromInput(...)   $entityInstance.toOutput()
```

### Guidelines

+ The `$Entity` `fromInput` static method is responsible for input validation.
+ The `$Entity` `toOutput` method, and more generally the `Output$Entity` classes set to which depth objects will be serialized. The `$Entity` `toOutput` method should call the `toOutput` method on `$Entity` fields which are other entities.