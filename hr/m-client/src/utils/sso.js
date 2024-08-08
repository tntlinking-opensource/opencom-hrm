export const storage = {
    repository: {
        getItem(repositoryType, key) {
            try {
                return window[`${repositoryType}`].getItem(key);
            } catch (e) {
                throw new Error('根据[' + `${key}` + ']键,获取值异常：[' + `${e.message}` + ']');
            }
        },
        setItem(repositoryType, key, value) {
            window[`${repositoryType}`].setItem(key, value);
        },
        removeItem(repositoryType, key) {
            window[`${repositoryType}`].removeItem(key);
        },
        clear(repositoryType) {
            window[`${repositoryType}`].clear();
        }
    }
};