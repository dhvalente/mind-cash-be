package br.com.mindcash.financial.driving.http.income.jsons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record CategoryJson(String description, String type) {
}