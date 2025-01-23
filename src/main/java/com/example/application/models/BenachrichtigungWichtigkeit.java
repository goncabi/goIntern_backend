package com.example.application.models;

/**
 * Enum, der dei Wichtigkeit einer Nachricht bestimmt.
 * Es wird verwendet, um beim Löschen Nachrichten nach wichtig und unwichtig zu selektieren,
 * um das Löschen wichtiger Nachrichten zu verhindern.
 */
public enum BenachrichtigungWichtigkeit {
    /**
     * Eine Nachricht mit 'Wichtig' wird nicht gelöscht
     */
    WICHTIG,
    /**
     * Eine Nachricht mit 'Unwichtig' wird in bestimmten Fällen gelöscht
     */
    UNWICHTIG
}
