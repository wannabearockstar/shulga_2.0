function GroupInfo(group, curriculum) {
    this.group = group || new Group();
    this.curriculum = curriculum || [ new GroupCurriculum() ];
}

function GroupCurriculum(professor, discipline, lesson_type) {
    this.professor = professor || new Professor();
    this.discipline = discipline || new Discipline();
    this.lesson_type = lesson_type || new LessonType();
}

function Group(id, alias) {
    this.id = id || 0;
    this.alias = alias || "";
}

Group.fromId = function (id) {
    if (typeof (id) !== 'number')
        return new Group();

    var tmp = bind("group", 'id', id);
    if (typeof (tmp) !== 'object')
        return new Group();

    return new Group(tmp.id, tmp.alias);
};

function Professor(id, first_name, last_name, middle_name) {
    this.id = id || 0;
    this.first_name = first_name || "";
    this.last_name = last_name || "";
    this.middle_name = middle_name || "";
}

Professor.fromId = function (id) {
    if (typeof (id) !== 'number')
        return new Professor();

    var tmp = bind("professor", "id", id);
    if (typeof (tmp) !== 'object')
        return new Professor();

    return new Professor(
        tmp.id,
        tmp.first_name,
        tmp.last_name,
        tmp.middle_name
    );
};

function Discipline(id, alias) {
    this.id = id || 0;
    this.alias = alias || "";
}

Discipline.fromId = function (id) {
    if (typeof (id) !== 'number')
        return new Discipline();

    var tmp = bind("discipline", "id", id);
    if (typeof (tmp) !== 'object')
        return new Discipline();

    return new Discipline(tmp.id, tmp.alias);
};

function LessonType(id, alias) {
    this.id = id || 0;
    this.alias = alias || "";
}

LessonType.fromId = function (id) {
    if (typeof (id) !== 'number')
        return new LessonType();

    var tmp = bind("lesson_type", "id", id);
    if (typeof (tmp) !== 'object')
        return new LessonType();

    return new LessonType(tmp.id, tmp.alias);
};

function Auditory(id, alias, lat, lon, level, building_id) {
    this.id = id || 0;
    this.alias = alias || "";
    this.lat = lat || 0.0;
    this.lon = lon || 0.0;
    this.level = level || 0;
    this.building_id = building_id || 0;
}

Auditory.fromId = function (id) {
    if (typeof (id) !== 'number')
        return new Auditory();

    var tmp = bind("auditory", "id", id);
    if (typeof (tmp) !== 'object')
        return new Auditory();

    return new Auditory(
        tmp.id,
        tmp.alias,
        tmp.lat,
        tmp.lon,
        tmp.level,
        tmp.building_id
    );
};

function bind(entity_name, param_name, id) {
    var entities = utils.entity.list(entity_name);
    return _.find(entities, function (item) {
        return item[param_name] = id;
    });
}

function Status(finished, progress, fitness, maxFitness) {
    this.finished = finished || false;
    this.progress = progress || 0.0;
    this.fitness = fitness || 0.0;
    this.maxFitness = maxFitness || false;
}