import os
import javalang  # Install via pip: pip install javalang

def parse_java_files(directory):
    classes = {}
    associations = set()
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.java'):
                with open(os.path.join(root, file), 'r') as f:
                    content = f.read()
                    try:
                        tree = javalang.parse.parse(content)
                        for type_decl in tree.types:
                            if isinstance(type_decl, javalang.tree.ClassDeclaration):
                                class_name = type_decl.name
                                extends = type_decl.extends.name if type_decl.extends else None
                                methods = [m.name for m in type_decl.methods]
                                field_types = []
                                for field in type_decl.fields:
                                    if hasattr(field.type, 'name'):
                                        field_types.append(field.type.name)
                                    else:
                                        field_types.append(str(field.type))
                                classes[class_name] = {'extends': extends, 'methods': methods, 'field_types': field_types}
                                # Add associations
                                for field_type in field_types:
                                    if field_type in classes:
                                        associations.add((class_name, field_type))
                    except Exception as e:
                        print(f"Error parsing {file}: {e}")
    return classes, associations

# Generate PlantUML
classes, associations = parse_java_files('.')
with open('diagram.puml', 'w') as f:
    f.write('@startuml\n')
    for cls, info in classes.items():
        f.write(f'class {cls} {{\n')
        for method in info['methods'][:5]:  # Limit for brevity
            f.write(f'  +{method}()\n')
        f.write('}\n')
        if info['extends']:
            f.write(f'{cls} --|> {info["extends"]}\n')
    for assoc in associations:
        f.write(f'{assoc[0]} --> {assoc[1]}\n')
    f.write('@enduml\n')


# To generate the UML diagram, run the following command in your terminal:
# java -jar plantuml.jar diagram.puml