package com.kobot.framework.entitysystem.components.api.basic;

/**
 * Instance of SharedComponent can be shared among many Entities
 * eg. Team
 * Two Entities belongs to the same Team so they share common Team component
 */
public interface SharedComponent extends Component {
}
