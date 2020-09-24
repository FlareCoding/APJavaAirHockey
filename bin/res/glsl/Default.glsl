#shader vertex
#version 330 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec2 in_UV;
layout(location = 2) in vec3 in_Normal;

out vec2 UV;

uniform mat4 u_Transform;
uniform mat4 u_Projection;
uniform mat4 u_ViewMatrix;

void main()
{
	UV = in_UV;
	gl_Position = u_Projection * u_ViewMatrix * u_Transform * vec4(in_Position, 1.0);
}

#shader pixel
#version 330 core

in vec2 UV;

layout(location = 0) out vec4 o_FinalColor;

uniform sampler2D textureSampler;

uniform vec4 u_Color;

void main()
{
	vec4 color = texture(textureSampler, UV) * u_Color;

	if (color.a < 0.7)
		discard;

	o_FinalColor = color;
}
