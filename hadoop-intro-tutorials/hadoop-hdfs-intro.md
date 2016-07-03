Hadoop is an open-source framework that allows to store and process big data in a 
distributed environment across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage.

Please refer to this link for brief introduction to Big Data term explanation: http://www.tutorialspoint.com/hadoop/hadoop_big_data_overview.htm.

![Hadoop Logo](hadoop_full.jpg)
There are several approaches to solving tasks of processing huge amounts of data. The first major breakthrough was made by Google when they
developed MapReduce algorithm and used it in the tasks of searching, sorting and indexing data in their systems.

This algorithm divides the task into small parts and assigns those parts to many computers connected over the network, and collects the results to form the final result dataset.

Hadoop is a java framework based on Google's Map Reduce approach, created in order to facilitate Big Data programs development. In short, Hadoop framework is capable enough to develop applications capable of running on clusters of computers and they could perform complete statistical analysis for a huge amounts of data.

**Hadoop Architecture**

For the architecture please refer to http://www.tutorialspoint.com/hadoop/hadoop_introduction.htm.

**Hadoop installation**

For the Installation process please revise this link https://hadoop.apache.org/docs/r2.7.2/hadoop-project-dist/hadoop-common/SingleCluster.html.

There are several ways to operate Hadoop cluster:

1._Local/Standalone Mode_.
After downloading Hadoop in your system, by default, it is configured in a standalone mode and can be run as a single java process.

2._Pseudo Distributed Mode_.
It is a distributed simulation on single machine. Each Hadoop daemon such as hdfs, yarn, MapReduce etc., will run as a separate java process. This mode is useful for development.

2._Fully Distributed Mode_.
This mode is fully distributed with minimum two or more machines as a cluster. We will come across this mode in detail in the coming chapters.

We will use Hadoop in Pseudo Distributed Mode. For the instructions on setting up environment for running in that mode and checking out functioning of the cluster refer to: http://www.tutorialspoint.com/hadoop/hadoop_enviornment_setup.htm.

If everything is fine the cluster is now completely ready to run some Map Reduce jobs. But lets first get familiar with the most important component of Hadoop ecosystem such as **HDFS**.

For the **HDFS architecture** follow this link http://www.tutorialspoint.com/hadoop/hadoop_hdfs_overview.htm.

For the **Operations on HDFS** refer to http://www.tutorialspoint.com/hadoop/hadoop_hdfs_operations.htm and to http://www.tutorialspoint.com/hadoop/hadoop_command_reference.htm.

Map Reduce algotithm in more details http://www.tutorialspoint.com/hadoop/hadoop_mapreduce.htm.