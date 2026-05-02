package com.vault.core.domain.model.enums;

public enum ModerationActionType {
    SUSPEND_USER("Cuenta de usuario suspendida temporalmente por infracción de normas"),
    DELETE_USER("Cuenta de usuario eliminada de forma permanente (borrado lógico)"),
    UNSUSPEND_USER("Suspensión levantada; acceso restaurado a la cuenta del usuario"),
    REMOVE_AVATAR("Archivo multimedia avatar eliminado del perfil por contenido inapropiado"),
    CENSOR_PROFILE_TEXT("Texto ofensivo eliminado de la información pública del perfil del usuario"),
    DELETE_POST("Publicación eliminada u ocultada por incumplimiento de las normas de la plataforma"),
    DISMISS_REPORT("Reporte revisado y desestimado; no se encontró infracción a las normas");

    private final String description;

    ModerationActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
