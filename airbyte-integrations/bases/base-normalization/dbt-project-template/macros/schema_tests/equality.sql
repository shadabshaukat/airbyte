{#
-- Adapted from https://github.com/fishtown-analytics/dbt-utils/blob/bbba960726667abc66b42624f0d36bbb62c37593/macros/schema_tests/equality.sql
-- dbt-utils version: 0.6.4
-- This macro needs to be updated accordingly when dbt-utils is upgraded.
-- This is needed because MySQL does not support the EXCEPT operator!
#}

{% macro mysql__test_equality(model, compare_model, compare_columns=None) %}

    {% set set_diff %}
        count(*) + coalesce(abs(
            sum(case when which_diff = 'a_minus_b' then 1 else 0 end) -
            sum(case when which_diff = 'b_minus_a' then 1 else 0 end)
        ), 0)
    {% endset %}

    {{ config(fail_calc = set_diff) }}

    {%- if not execute -%}
        {{ return('') }}
    {% endif %}

    {%- do dbt_utils._is_relation(model, 'test_equality') -%}

    {%- if not compare_columns -%}
        {%- do dbt_utils._is_ephemeral(model, 'test_equality') -%}
        {%- set compare_columns = adapter.get_columns_in_relation(model) | map(attribute='quoted') -%}
    {%- endif -%}

    {% set compare_cols_csv = compare_columns | join(', ') %}

    with a as (
        select * from {{ model }}
    ),

    b as (
        select * from {{ compare_model }}
    ),

    a_minus_b as (
        select {{ compare_cols_csv }} from a
        where ({{ compare_cols_csv }}) not in
            (select {{ compare_cols_csv }} from b)
    ),

    b_minus_a as (
        select {{ compare_cols_csv }} from b
        where ({{ compare_cols_csv }}) not in
            (select {{ compare_cols_csv }} from a)
    ),

    unioned as (
        select * from a_minus_b
        union all
        select * from b_minus_a
    ),

    final as (
        select (select count(*) from unioned) +
        (select abs(
            (select count(*) from a_minus_b) -
            (select count(*) from b_minus_a)
            ))
        as count
    )

    select count from final

{% endmacro %}
