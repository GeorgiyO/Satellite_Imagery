export {getParentType, getChildType};

function getParentType(type) {
    switch (type) {
        case "country": return null;
        case "region": return "country";
        case "city": return "region";
        case "attraction": return "city";
    }
}

function getChildType(type) {
    switch (type) {
        case "country": return "region";
        case "region": return "city";
        case "city": return "attraction";
        case "attraction": return null;
    }
}