/**
 * Returns File instance
 * @param file relative path to file or file itself
 * @return java.io.File
 */
function toFile(file) {
    return typeof file == 'string' ? new File(file, getFolder()) : file;
}

/**
 * Creates a list from given elements
 * @param items items to be pushed into the list
 * @return java.util.ArrayList<?>
 */
function list(...items) {
    var list = new ArrayList();
    for (let item of items) {
        list.add(item);
    }
    return list;
}