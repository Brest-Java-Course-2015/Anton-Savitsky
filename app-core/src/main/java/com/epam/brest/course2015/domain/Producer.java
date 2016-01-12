package com.epam.brest.course2015.domain;

/**
 * Created by antonsavitsky on 02.12.15.
 */
public class Producer {
    private Integer producerId;
    private String producerName;
    private String country;

    public Producer(Integer producerId, String producerName, String country){
        this.producerId=producerId;
        this.producerName=producerName;
        this.country=country;
    }

    public Producer(String producerName, String country){
        this.producerName=producerName;
        this.country=country;
    }

    public Producer(String producerName){
        this.producerName=producerName;
    }

    public Producer(){}

    public Producer(Integer producerId){
        this.producerId=producerId;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer  producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public enum ProducerFields {
        PRODUCER_ID("producerId"),
        PRODUCER_NAME("producerName"),
        COUNTRY("country");
        ProducerFields(String value) {
            this.value = value;
        }
        private final String value;
        public String getValue() { return value; }
    }

    @Override
    public String toString() {
        return String.format("Producer: {" +
                "producerId=" + producerId +
                ", producerName='" + producerName +
                ", country=" + country +
                '}');
    }
}
