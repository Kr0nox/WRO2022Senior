# WRO 2020 Senior Challenge

Code for the [2022 World Robot Olympiad Season](https://wro-association.org/competition/2022-season/) Senior Category. </br>
From [Team BrickFire](https://brickfire.team). Taking Part in [Germany](https://www.worldrobotolympiad.de). </br>
Current stage: [Regional competition Bad Lear (Region Osnabrück)](https://www.worldrobotolympiad.de/saison-2022/wettbewerbe/295/bad-laer-region-osnabrueck)
</br>

## Structure
### Packages

**robotParts:** </br> 
Everything related to the robot goes here. In the blueprint there are already a base robot class and movement controllers. </br>
Here classes like arms or functional attachments, as well as sensor parts or other robot related stuff.
</br></br>
**actions:** </br>
Here the classes that hold the instructions for executing the tasks go. Add as many as necessary. A given "main"-class is already
given by the blueprint. In its execute-Method, you should order all your methods from your own classes. 
Feel free to add attributes for objects of these classes.
Suggested is that a class for every sub challenge is added, so values relevant to those missions can be stored
in objects of the relating type. They should extend the abstract class "BaseAction"
</br></br>
**challengeParts:** </br>
Here classes for objects of the challenge will be written. Whether it be a block, of which the color needs to be stored or something
else. A class which creates objects that store those values will be written in here.
</br>

### Classes (how the blueprint should be extended and used)
**RunWRO:** </br>
This class needs to be build as a jar and put on the EV3
</br></br>
**Robot:** </br>
Add objects for the sensors and functional motor parts or other stuff related to the robot into this class
</br></br>
**ActionsMain:** </br>
Add objects for your sub-challenges (please let them inherit from BaseAction). Declare them in the Constructor.
All the tasks, like calling methods from your objects, happens in the execute-method.
</br></br>
**BaseAction:** </br>
All Action-classes, so classes for sub-challenges, should extend from this class

