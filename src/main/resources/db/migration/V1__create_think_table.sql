create table if not exists think(
    id uuid not null primary key,
    body varchar(500),
    title varchar(250),
    created_at timestamp(6)

)