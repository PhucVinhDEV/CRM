CREATE OR REPLACE PROCEDURE delete_attribute_and_values(IN aid INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM J_NUMBERATTRIBUTEVALUE WHERE attribute_id = aid;
    DELETE FROM J_DATEATTRIBUTEVALUE WHERE attribute_id = aid;
    DELETE FROM J_STRINGATTRIBUTEVALUE WHERE attribute_id = aid;
    DELETE FROM J_ATTRIBUTE WHERE attribute_id = aid;
END;
$$;