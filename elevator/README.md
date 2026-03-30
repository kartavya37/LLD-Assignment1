# Elevator LLD Demo

This module implements a multi-elevator system with outside/inside panels, dispatch strategy selection, maintenance mode, emergency broadcast, dynamic floor extension, and weight safety checks.

## UML Diagram (Schema View)

```mermaid
flowchart TB
    OP[OutsidePanel per floor\nUP/DOWN buttons]
    ES[ElevatorSystem\nmultiple elevators\nassign + stepAll]
    STR[Dispatch Strategy\nFCFS / Shortest Seek]

    CAR1[ElevatorCar-1]
    CAR2[ElevatorCar-2]
    CARN[ElevatorCar-N]

    IP[InsidePanel\nfloor buttons\nopen/close/alarm]

    MW[Maintenance Mode]
    EM[Emergency Broadcast]
    WL[Weight Limit Guard]

    OP --> ES
    STR --> ES

    ES --> CAR1
    ES --> CAR2
    ES --> CARN

    CAR1 --> IP
    CAR2 --> IP
    CARN --> IP

    MW --> CAR1
    MW --> CAR2
    EM --> CAR1
    EM --> CAR2
    WL --> CAR1
    WL --> CAR2
```

## Class Diagram (Code-Level)

```mermaid
classDiagram
    class ElevatorSystem {
        -List~ElevatorCar~ cars
        -Map~Integer,OutsidePanel~ outsidePanels
        -Map~String,Integer~ activeFloorAssignments
        -ElevatorDispatchStrategy strategy
        -int maxFloor
        +requestFromFloor(floor, direction) void
        +stepAll() void
        +setMaintenance(carId, enabled) void
        +emergencyAlarmAll() void
        +addFloors(additionalFloors) void
    }

    class ElevatorCar {
        -int carId
        -int maxWeight
        -int maxFloor
        -int currentFloor
        -int currentWeight
        -ElevatorState state
        -DoorState doorState
        -boolean emergencyMode
        +addStop(floor) void
        +step() void
        +openDoor() void
        +closeDoor() void
        +ringAlarm() void
        +setMaintenance(enabled) void
        +addLoad(weight) void
    }

    class InsidePanel {
        +pressFloor(floor) void
        +pressOpenDoor() void
        +pressCloseDoor() void
        +pressAlarm() void
    }

    class OutsidePanel {
        +pressUp() OutsideRequest
        +pressDown() OutsideRequest
    }

    class OutsideRequest {
        -int floor
        -Direction direction
        +key() String
    }

    class ElevatorDispatchStrategy {
        <<interface>>
        +selectElevator(cars, request) ElevatorCar
    }

    class FcfSElevatorStrategy
    class ShortestSeekStrategy

    class Direction {
        <<enum>>
        UP
        DOWN
        NONE
    }

    class ElevatorState {
        <<enum>>
        UP
        DOWN
        IDLE
        UNDER_MAINTENANCE
    }

    class DoorState {
        <<enum>>
        OPEN
        CLOSED
    }

    ElevatorDispatchStrategy <|.. FcfSElevatorStrategy
    ElevatorDispatchStrategy <|.. ShortestSeekStrategy

    ElevatorSystem --> ElevatorCar
    ElevatorSystem --> OutsidePanel
    ElevatorSystem --> ElevatorDispatchStrategy
    OutsidePanel --> OutsideRequest
    ElevatorCar --> InsidePanel
```

## Requirements Coverage

- Multi-elevator support via `ElevatorSystem` with `List<ElevatorCar>`.
- Single outside panel per floor implemented as `OutsidePanel` map (`floor -> panel`).
- Inside panel per elevator with floor/open/close/alarm actions.
- Dispatch strategy pattern implemented:
  - `FcfSElevatorStrategy`
  - `ShortestSeekStrategy`
- Top floor supports only DOWN; ground floor supports only UP.
- One elevator assignment per floor-direction at a time using active assignment map.
- Supports both UP and DOWN requests on same floor as separate keys.
- Weight limit per car: if exceeded, door stays open and warning sound is printed.
- Maintenance mode: car transitions to `UNDER_MAINTENANCE` and stops operating.
- Emergency: all elevators stop, open doors, and ring alarms.
- Elevator states covered: `UP`, `DOWN`, `IDLE`, `UNDER_MAINTENANCE`.
- Floors can be added dynamically with `addFloors()`.

## Build & Run

From project root (`elevator`):

```bash
javac src/com/example/elevator/*.java
java -cp src com.example.elevator.App
```

## Demo Flow in App

- outside calls across floors
- inside panel floor selection
- weight overflow scenario
- maintenance toggle
- emergency broadcast
- dynamic floor addition
