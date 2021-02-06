
/**
 * Returns active Minecraft version (from settings > Minecraft > Server version)
 * @return java.lang.String
 */
function getMinecraftVersion() {
    return chorus.config.get('4.Minecraft.0.Server_version');
}

/**
 * Utility function. Gets a McClass of given element
 * @param element game element class name
 * @return org.chorusmc.chorus.minecraft.McClass
 */
function getMcClass(element) {
    var McClass = chorus_type('minecraft.McClass');
    return new McClass(element);
}

/**
 * Gets all items
 * @return org.chorusmc.chorus.minecraft.item.Item[]
 */
function getItems() {
    return getMcClass('Item').getEnumValues();
}

/**
 * Gets an item by name
 * @param name item name
 * @return org.chorusmc.chorus.minecraft.item.Item
 */
function getItem(name) {
    return getMcClass('Item').valueOf(name);
}

/**
 * Gets all entities
 * @return org.chorusmc.chorus.minecraft.entity.Entity[]
 */
function getEntities() {
    return getMcClass('Entity').getEnumValues();
}

/**
 * Gets an entity by name
 * @param name entity name
 * @return org.chorusmc.chorus.minecraft.entity.Entity
 */
function getEntity(name) {
    return getMcClass('Entity').valueOf(name);
}

/**
 * Gets all enchantments
 * @return org.chorusmc.chorus.minecraft.enchantment.Enchantment[]
 */
function getEnchantments() {
    return getMcClass('Enchantment').getEnumValues();
}

/**
 * Gets an enchantment by name
 * @param name enchantment name
 * @return org.chorusmc.chorus.minecraft.enchantment.Enchantment
 */
function getEnchantment(name) {
    return getMcClass('Enchantment').valueOf(name);
}

/**
 * Gets all effects
 * @return org.chorusmc.chorus.minecraft.effect.Effect[]
 */
function getEffects() {
    return chorus_type('minecraft.effect.Effect').values();
}

/**
 * Gets an effect by name
 * @param name effect name
 * @return org.chorusmc.chorus.minecraft.effect.Effect
 */
function getEffect(name) {
    return chorus_type('minecraft.effect.Effect').valueOf(name);
}

/**
 * Gets all particles
 * @return org.chorusmc.chorus.minecraft.particle.Particle[]
 */
function getParticles() {
    return chorus_type('minecraft.particle.Particle').values();
}

/**
 * Gets a particle by name
 * @param name particle name
 * @return org.chorusmc.chorus.minecraft.particle.Particle
 */
function getParticle(name) {
    return chorus_type('minecraft.particle.Particle').valueOf(name);
}

/**
 * Gets all sounds
 * @return org.chorusmc.chorus.minecraft.sound.Sound[]
 */
function getSounds() {
    return chorus_type('minecraft.sound.Sound').values();
}

/**
 * Gets a sound by name
 * @param name sound name
 * @return org.chorusmc.chorus.minecraft.sound.Sound
 */
function getSound(name) {
    return chorus_type('minecraft.sound.Sound').valueOf(name);
}

/**
 * Converts a string to a conversion-ready time unit
 * ms/millis/milliseconds -> MILLISECONDS
 * sec/seconds            -> SECONDS
 * min/minutes            -> MINUTES
 * hr/hours               -> HOURS
 * days                   -> DAYS
 * @param timeUnit unit as string
 * @return org.chorusmc.chorus.minecraft.ticks.TimeUnit
 */
function toTicksTimeUnit(timeUnit) {
    var TimeUnit = chorus_type('minecraft.tick.TimeUnit');
    switch (timeUnit.toLowerCase()) {
        case 'ms':
        case 'millis':
        case 'milliseconds':
            return TimeUnit.MILLISECONDS;
        case 'sec':
        case 'seconds':
            return TimeUnit.SECONDS;
        case 'min':
        case 'minutes':
            return TimeUnit.MINUTES;
        case 'hr':
        case 'hours':
            return TimeUnit.HOURS;
        case 'days':
            return TimeUnit.DAYS;
    }
}

/**
 * Converts a time unit to ticks
 * @param type unit type (@see toTicksTimeUnit)
 * @param time amount of time
 * @return Int
 */
function timeToTicks(type, time) {
    var Converter = chorus_type('minecraft.tick.TicksConverter');
    return parseInt(new Converter().convert(toTicksTimeUnit(type), time.toString()));
}

/**
 * Converts ticks to a time unit
 * @param type unit type (@see toTicksTimeUnit)
 * @param ticks amount of ticks
 * @return Float
 */
function ticksToTime(type, ticks) {
    var Converter = chorus_type('minecraft.tick.TicksConverter');
    return parseFloat(new Converter().revert(toTicksTimeUnit(type), ticks.toString()));
}

/**
 * Instantiates a Chorus' colored-text parser
 * @param text text to parse
 * @param useVariables optional boolean which defines to replace variables or not
 * @constructor
 */
function ChatParser(text, useVariables) {
    var ParserClass = chorus_type('minecraft.chat.ChatParser');
    return new ParserClass(text, useVariables ? useVariables : true);
}

/**
 * Color prefix from settings
 * @return java.lang.String
 */
function getColorPrefix() {
    return chorus_type('util.VarsKt').getColorPrefix();
}

/**
 * Replaces a standard color prefix with the current color prefix in a text
 * @param text text
 * @param prefix (optional) prefix to be translated. '&' if not specified
 * @return java.lang.String
 */
function translateColorPrefixes(text, prefix) {
    return text.replace(prefix ? prefix : '&', getColorPrefix());
}

/**
 * Removes color/formats codes from given text
 * @param text text with codes
 * @return java.lang.String
 */
function removeColorCodes(text) {
    return ChatParser(translateColorPrefixes(text), false).toPlainText();
}

/**
 * Parses a colored text into a JavaFX node (TextFlow)
 * @param text text to be parsed
 * @param useVariables whether or not variables should be replaced. True if omitted
 * @return javafx.scene.text.TextFlow
 */
function coloredTextToNode(text, useVariables) {
    return new ChatParser(translateColorPrefixes(text), useVariables).toTextFlow(true);
}

/**
 * Instantiates a Chorus' variable
 * @param name variable name
 * @param value variable value
 * @return org.chorusmc.chorus.variable.Variable
 */
function Variable(name, value) {
    var VariableClass = chorus_type('variable.Variable');
    return new VariableClass(name, value);
}

/**
 * Gets variables
 * @return java.util.List<org.chorusmc.chorus.variable.Variable>
 */
function getVariables() {
    return chorus_type('variable.Variables').getVariables();
}

/**
 * Gets a variable by name
 * @param name variable name
 * @return org.chorusmc.chorus.variable.Variable
 */
function getVariable(name) {
    return getVariables()
        .stream()
        .filter(variable => variable.getName() == name)
        .findAny()
        .orElse(null);
}

/**
 * Creates a GUI-format
 * @param getProperties function that returns a list of: getName(), getRows() and getItems()
 */
function GUIFormat(getName, getRows, getItems) {
    var FormatClass = chorus_type('menus.drop.actions.previews.GUIFormat');
    var FormatExtender = Java.extend(FormatClass, {
        getName: getName,
        getRows: getRows,
        getItems: getItems
    })
    return new FormatExtender();
}

/**
 * Creates a GUI-format position instance
 * @param x either slot number (y must be undefined) or x-slot
 * @param y optional y-slot
 * @return menus.drop.actions.previews.GUIFormatPosition
 */
function GUIFormatPosition(x, y) {
    var PositionClass = chorus_type('menus.drop.actions.previews.GUIFormatPosition');
    return y ? new PositionClass(x, y) : new PositionClass(x);
}

/**
 * Creates a GUI-format item instance
 * @param position item position
 * @param item item string name
 * @param meta optional item meta (< 1.12)
 */
function GUIFormatItem(position, item, meta) {
    var ItemClass = chorus_type('menus.drop.actions.previews.GUIFormatItem');
    return new ItemClass(position, item, meta ? meta : 0);
}