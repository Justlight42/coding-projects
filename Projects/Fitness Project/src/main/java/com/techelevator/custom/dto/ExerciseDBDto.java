package com.techelevator.custom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExerciseDBDto {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("bodyPart")
    private final String bodyPart;
    @JsonProperty("target")
    private final String specificMuscle;
    @JsonProperty("equipment")
    private final String equipment;
    @JsonProperty("gifUrl")
    private final String image;
    @JsonProperty("instructions")
    private final String[] description;

    public ExerciseDBDto(String name, String bodyPart, String specificMuscle, String equipment, String image, String[] description) {
        this.name = name;
        this.bodyPart = bodyPart;
        this.specificMuscle = specificMuscle;
        this.equipment = equipment;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public String getSpecificMuscle() {
        return specificMuscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return String.join(" ", description);
    }

}
