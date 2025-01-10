Drop PROCEDURE IF EXISTS delete_attribute_and_values(INT);
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


DROP FUNCTION IF EXISTS get_all_attribute_values(UUID);
CREATE OR REPLACE FUNCTION get_all_attribute_values(input_customerid UUID)
    RETURNS TABLE (
                      attribute_name TEXT,
                      value TEXT
                  ) AS $$
BEGIN
    RETURN QUERY
        SELECT ja.j_name::TEXT, na.value::TEXT
        FROM J_NUMBERATTRIBUTEVALUE na
                 INNER JOIN public.j_attribute ja on ja.attribute_id = na.attribute_id
        WHERE na.customer_id = input_customerid

        UNION

        SELECT j.j_name::TEXT, da.value::TEXT
        FROM J_DATEATTRIBUTEVALUE da
                 INNER JOIN public.j_attribute j on j.attribute_id = da.attribute_id
        WHERE da.customer_id = input_customerid

        UNION

        SELECT a.j_name::TEXT, sa.value::TEXT
        FROM J_STRINGATTRIBUTEVALUE sa
                 INNER JOIN public.j_attribute a on a.attribute_id = sa.attribute_id
        WHERE sa.customer_id = input_customerid;
END;
$$ LANGUAGE plpgsql;