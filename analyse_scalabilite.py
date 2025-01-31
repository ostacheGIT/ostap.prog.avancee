import matplotlib.pyplot as plt

# Données de scalabilité forte
# Format: (nb_process, speedup)
data = [
    (1, 1.0),
    (2, 1.6),
    (4, 3.1),
    (8, 5.8),
    (16, 5.5),
    (32, 6.8)
]

# Séparer les données en deux listes: nb_process et speedup
nb_process = [x[0] for x in data]
speedup = [x[1] for x in data]

# Tracer la courbe
plt.figure(figsize=(10, 6))
plt.plot(nb_process, speedup, marker='o', linestyle='-', color='b')

# Ajouter des labels et un titre
plt.xlabel('Number of Processes')
plt.ylabel('Speedup')
plt.title('Strong Scalability Curve')
plt.grid(True)

# Afficher la courbe
plt.show()