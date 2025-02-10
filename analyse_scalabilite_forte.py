import numpy as np
import matplotlib.pyplot as plt


def read_data(file_path):
    with open(file_path, 'r') as file:
        data = [line.strip().replace(',', '.').split('\t') for line in file if line.strip()]
    return np.array(data[1:], dtype=float)


def calculate_speedup(data):
    temps_execution = data[:, 1]  # temps_exec
    nombre_process = data[:, 0]  # Workers

    T1 = np.median(temps_execution[nombre_process == 1])

    unique_processes = np.unique(nombre_process)
    Tp = []

    for p in unique_processes:
        Tp.append(np.median(temps_execution[nombre_process == p]))

    Sp = T1 / np.array(Tp)

    return Sp, unique_processes


def plot_speedup(speedup_data, nombre_process_data, ntot_values):
    plt.figure(figsize=(10, 6))

    for speedup, nombre_process, ntot in zip(speedup_data, nombre_process_data, ntot_values):
        plt.plot(nombre_process, speedup, marker='o', label=f'ntot = {int(ntot)}')

    plt.title('Strong Scalability Curve')
    plt.xlabel('Number of Processes')
    plt.ylabel('Speedup')
    plt.axhline(1, color='red', linestyle='--', label='Speedup = 1')

    max_process = max(max(nombre_process) for nombre_process in nombre_process_data)
    plt.plot([1, max_process], [1, max_process], color='blue', linestyle='--', label='Ideal Scalability')

    plt.legend()
    plt.grid()

    plt.xlim(left=0)
    plt.ylim(bottom=0)
    plt.gca().set_aspect('equal', adjustable='box')

    # x_ticks = np.arange(0, max_process + 1, 1)
    #y_ticks = np.arange(0, max(max(speedup) for speedup in speedup_data) + 1,
    #                    1)
    #plt.xticks(x_ticks)
    #plt.yticks(y_ticks)

    plt.show()


# Main
if __name__ == "__main__":
    file_path = 'pi_scalability.txt'
    data = read_data(file_path)
    ntot_values = np.unique(data[:, 5])

    speedup_data = []
    nombre_process_data = []

    for ntot in ntot_values:
        filtered_data = data[data[:, 5] == ntot]
        speedup, nombre_process = calculate_speedup(filtered_data)

        speedup_data.append(speedup)
        nombre_process_data.append(nombre_process)

    plot_speedup(speedup_data, nombre_process_data, ntot_values)
