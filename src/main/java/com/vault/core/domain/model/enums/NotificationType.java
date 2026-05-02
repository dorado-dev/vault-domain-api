package com.vault.core.domain.model.enums;

public enum NotificationType {
    NEW_FOLLOWER("¡Tienes un nuevo seguidor en tu perfil!"),
    POST_REACTION("Alguien ha reaccionado a tu publicación"),
    POST_COMMENT("Alguien ha dejado un comentario en tu publicación"),
    MODERATION_WARN("Aviso importante del equipo de moderación sobre tu cuenta"),
    REPORT_FEEDBACK("Hemos revisado el reporte que enviaste. Gracias por ayudar a la comunidad"),
    POST_DELETED_BY_MOD("Una de tus publicaciones ha sido eliminada por infringir nuestras normas"),
    NEW_REPORT_PENDING("Sistema: Nuevo reporte de usuario pendiente de revisión urgente");

    private final String messageTemplate;

    NotificationType(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

}
