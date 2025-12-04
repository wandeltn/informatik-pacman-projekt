import os
import javalang  # Install via pip: pip install javalang

def parse_java_files(directory):
    classes = {}
    associations = set()
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.java'):
                try:
                    with open(os.path.join(root, file), 'r', encoding='utf-8') as f:
                        content = f.read()
                    tree = javalang.parse.parse(content)
                    for type_decl in tree.types:
                        if isinstance(type_decl, javalang.tree.ClassDeclaration):
                            class_name = type_decl.name
                            extends = type_decl.extends.name if type_decl.extends else None
                            methods = [m.name for m in type_decl.methods]
                            fields = []
                            field_types = set()
                            for field in type_decl.fields:
                                field_type = field.type.name if hasattr(field.type, 'name') else str(field.type)
                                field_types.add(field_type)
                                # Collect field info
                                for declarator in field.declarators:
                                    visibility = '-' if field.modifiers and 'private' in field.modifiers else ('+' if field.modifiers and 'public' in field.modifiers else '#')
                                    fields.append(f'{visibility}{declarator.name}: {field_type}')
                            classes[class_name] = {'extends': extends, 'methods': methods, 'fields': fields, 'field_types': field_types}
                            # Add associations
                            for ft in field_types:
                                if ft in classes and ft != class_name:
                                    associations.add((class_name, ft))
                        else:
                            # Skip other types like records
                            pass
                except Exception as e:
                    print(f"Error parsing {file}: {e}")
                    continue
    return classes, associations

def get_label(cls, target):
    if cls == 'Spiel' and target in ['Pacman', 'Playingfield', 'GhostBlinky']:
        return 'manages'
    elif cls == 'GraphTraversal' and target == 'Node':
        return 'contains'
    elif cls == 'PelletManager' and target in ['Pellet', 'PowerDot']:
        return 'creates'
    elif cls == 'GhostBlinky' and target == 'Pacman':
        return 'targets'
    elif target == 'Logger':
        return 'uses'
    else:
        return 'has'


# Generate PlantUML
classes, associations = parse_java_files('.')
with open('diagram.puml', 'w', encoding='utf-8') as f:
    f.write('@startuml\n')
    for cls, info in classes.items():
        f.write(f'class {cls} {{\n')
        for field in info['fields'][:10]:  # Limit fields
            f.write(f'  {field}\n')
        for method in info['methods'][:5]:  # Limit methods
            f.write(f'  +{method}()\n')
        f.write('}\n')
        if info['extends']:
            f.write(f'{cls} --|> {info["extends"]}\n')
    for assoc in associations:
        label = get_label(assoc[0], assoc[1])
        f.write(f'{assoc[0]} --> {assoc[1]} : {label}\n')
    # Add known usages
    usages = [
        ('BaseGhost', 'Logger', 'uses'),
        ('GraphTraversal', 'Logger', 'uses'),
        ('AStar', 'Logger', 'uses'),
        ('PelletManager', 'Logger', 'uses'),
        ('GhostBlinky', 'Pacman', 'targets'),
        ('Figur', 'Zeichenfenster', 'wraps'),
    ]
    for u in usages:
        if u[0] in classes and u[1] in classes:
            f.write(f'{u[0]} ..> {u[1]} : {u[2]}\n')
    f.write('@enduml\n')


# To generate the UML diagram, run the following command in your terminal:
# java -jar plantuml.jar diagram.puml