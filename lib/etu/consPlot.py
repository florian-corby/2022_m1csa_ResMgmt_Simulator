import random
import sys
from itertools import cycle

import matplotlib.pyplot as plt


def plotConsumption(fileName):
    time = []
    totalCons = []
    counter = 0

    f = open(fileName, 'r')
    nbServers = int(f.read(1))
    serversCons = [[] for _ in range(nbServers)]

    for row in f.readlines()[1:]:
        # We format and split row input:
        row = row.replace('\n', '')
        row = row.split(" ")

        # We import row input in corresponding data structures:
        for i in range(len(row)-1):
            serversCons[i].append(float(row[i]))
        totalCons.append(float(row[len(row)-1]))

        # We manage time axis:
        time.append(counter)
        counter = counter + 1

    # We start plotting:
    fig = plt.figure()
    ax = plt.subplot(111)

    cycol = cycle('gcmyk')
    for i in range(nbServers):
        ax.plot(time, serversCons[i], color=next(cycol), label=('Power consumption of server ' + str(i)))
    ax.plot(time, totalCons, color='b', label='Total power over time')
    ax.plot(time, [600 for _ in range(len(totalCons))], color='r', label='Max power allowed')

    ax.set_xlabel('Time', fontsize=9)
    ax.xaxis.set_label_coords(1, -0.025)
    plt.ylabel('Watt')

    # Shrink current axis's height by 10% on the bottom
    box = ax.get_position()
    ax.set_position([box.x0, box.y0 + box.height * 0.1,
                     box.width, box.height * 0.9])

    # Put a legend below current axis
    ax.legend(loc='upper center', bbox_to_anchor=(0.5, -0.05), fancybox=True, shadow=True, ncol=5)

    plt.title('Servers Consumption Over Time')
    # plt.legend(facecolor='white', framealpha=1, loc='upper right')
    #plt.show()
    plt.savefig(fileName.rsplit('.', 1)[0] + ".png", bbox_inches = 'tight')


if __name__ == '__main__':
    plotConsumption(sys.argv[1])
