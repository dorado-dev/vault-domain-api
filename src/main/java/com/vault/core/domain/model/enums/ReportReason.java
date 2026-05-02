package com.vault.core.domain.model.enums;

public enum ReportReason {
    SPAM("Contenido comercial no deseado, enlaces sospechosos o comportamiento automatizado"),
    HARASSMENT("Acoso, insultos, intimidación o ataques personales dirigidos a un usuario"),
    HATE_SPEECH("Incitación al odio, discriminación o violencia contra grupos vulnerables"),
    INAPPROPRIATE_CONTENT("Contenido sexualmente explícito, violencia gráfica extrema o gore"),
    MISINFORMATION("Información falsa, engañosa o que busca manipular deliberadamente"),
    OTHER("Otra infracción a las normas de la comunidad no cubierta en las categorías anteriores");

    private final String description;

    ReportReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
