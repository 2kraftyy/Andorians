def move_object_behavior():

    # Read distance to object
    if arbiter.acquire("ultrasonic", "MOVE_OBJECT", 50):
        try:
            distance = mbuild.ultrasonic2.get()
        finally:
            arbiter.release("ultrasonic", "MOVE_OBJECT")

    # If nothing detected do nothing
    if distance is None or distance > 15:
        return

    # Acquire motors before moving
    if arbiter.acquire("motors", "MOVE_OBJECT", 50):
        try:
            # Turn 180 so back faces the object
            turn(180)


            # Move backward towards the object
            mbot2.straight(-(distance * 1.3))

            # Push object sideways
            mbot2.drive_speed(-30, 50)
            time.sleep(1.5)
            mbot2.drive_speed(0, 0)

            # Return to the original position
            mbot2.drive_speed(30, -50)
            time.sleep(1.5)
            mbot2.drive_speed(0, 0)

            # Step 7: Turn back to the original direction
            turn(180)

        finally:
            arbiter.release("motors", "MOVE_OBJECT")


@register_command("MOVE_OBJECT")
def handle_move_object(payload):
    move_object_behavior()
    return ok_response("MOVE_OBJECT complete!")
