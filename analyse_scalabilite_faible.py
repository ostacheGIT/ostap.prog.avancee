import numpy as np
import matplotlib.pyplot as plt

def read_data(file_path):
    with open(file_path, 'r') as file:
        data = [line.strip().replace(',', '.').split('\t') for line in file if line.strip()]

    # Convertir en numpy array (ignorer la première ligne = en-tête)
    return np.array(data[1:], dtype=float)

def calculate_speedup(data):
    temps_execution = data[:, 1]  # Temps d'exécution
    nombre_process = data[:, 0]   # Nombre de processus (Workers)

    # Prendre T1 comme le temps médian pour Workers = 1
    T1 = np.median(temps_execution[nombre_process == 1])

    unique_processes = np.unique(nombre_process)
    Tp = [np.median(temps_execution[nombre_process == p]) for p in unique_processes]

    Sp = T1 / np.array(Tp)
    return Sp, unique_processes

def plot_speedup(speedup, nombre_process):
    plt.figure(figsize=(10, 6))

    plt.plot(nombre_process, speedup, marker='o', linestyle='-', label='Speedup')

    plt.title('Weak Scalability Curve')
    plt.xlabel('Number of Processes')
    plt.ylabel('Speedup')
    plt.axhline(1, color='red', linestyle='--', label='Speedup = 1')

    max_process = max(nombre_process)

    plt.legend()
    plt.grid()
    plt.xlim(left=0)
    plt.ylim(bottom=0)
    plt.show()

# Main
if __name__ == "__main__":
    file_path = 'pi_scalability.txt'
    data = read_data(file_path)

    # On utilise toutes les données pour calculer le speedup
    speedup, nombre_process = calculate_speedup(data)

    # On trace une seule courbe pour tous les workers
    plot_speedup(speedup, nombre_process)
